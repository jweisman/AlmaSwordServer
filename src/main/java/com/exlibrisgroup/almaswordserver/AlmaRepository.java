package com.exlibrisgroup.almaswordserver;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.swordapp.server.AuthCredentials;
import org.swordapp.server.SwordEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.exlibrisgroup.almarestmodels.depositprofiles.DepositProfile;
import com.exlibrisgroup.almarestmodels.depositprofiles.DepositProfiles;
import com.exlibrisgroup.almarestmodels.representationfiles.RepresentationFile;
import com.exlibrisgroup.almarestmodels.representationfiles.RepresentationFiles;
import com.exlibrisgroup.almarestmodels.representations.Representation;
import com.exlibrisgroup.almarestmodels.representations.Representations;
import com.exlibrisgroup.almarestmodels.user.User;
import com.exlibrisgroup.almarestmodels.userdeposits.UserDeposit;
import com.exlibrisgroup.almaswordserver.SwordUtilities.AlmaProperties;
import com.exlibrisgroup.utilities.AlmaRestUtil;
import com.exlibrisgroup.utilities.Util;

public class AlmaRepository {
	
    private static Logger log = Logger.getLogger(AlmaRepository.class);
    private static AlmaProperties _properties = SwordUtilities.getAlmaProperties();

    private AuthCredentials _authCredentials;
    
    public AlmaRepository(AuthCredentials authCredentials) {
    	_authCredentials = authCredentials;
    }
	
    public void setAuthCredentials(AuthCredentials authCredentials) {
    	_authCredentials = authCredentials;
    }
    
	/*******************
	 * BIB
	********************/
    
	public String createBib(SwordEntry entry) throws Exception {

		String xml = 
			"<bib>" + 
			"<suppress_from_publishing>true</suppress_from_publishing>" +
			"<record_format>dc</record_format>" +
			"   <record xmlns:dc=\"http://purl.org/dc/elements/1.1/\">" +
			"      <dc:date>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</dc:date>";
		
		for (Map.Entry<String, List<String>> key : entry.getDublinCore().entrySet()) {
			for (String val : key.getValue()) {
				xml += "<dc:" + key.getKey() + ">" + val + "</dc:" + key.getKey() + ">";
			}
		}
		
		xml += "</record></bib>";
		
		String resp = almaPost("bibs", xml);
		
		String mms_id = Util.find(resp, "<mms_id>(\\d*)<\\/mms_id>");
		if (mms_id == "") {
			log.error("BIB could not be created: " + resp);
			throw new Exception("Could not create BIB");
		}
		log.info("BIB Created: " + mms_id);
		return mms_id;
	}
	
	public void updateBib(String mmsId, Map<String, List<String>> dc) {

		String resp = almaGet("bibs/" + mmsId);
		
		try {
		
			InputSource source = new InputSource(new StringReader(resp));
			
			Document document= DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(source);
	
			XPath xpath = XPathFactory.newInstance().newXPath();

			// Get "record" node
			Node record = (Node) xpath.evaluate("/bib/record", 
					document, XPathConstants.NODE);
			
			// For each received field, remove from source and replace with received fields
			for (String fieldName : dc.keySet()) {
				NodeList fields = (NodeList) xpath.evaluate("/bib/record/" + fieldName, 
						document, XPathConstants.NODESET);
				for (int i = 0; i < fields.getLength(); i++) {
					fields.item(i).getParentNode().removeChild(fields.item(i));
				}

				for (String val : dc.get(fieldName)) {
					Element field = document.createElement("dc:" + fieldName);
					field.appendChild(document.createTextNode(val));
					record.appendChild(field);
				}
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StringWriter sw = new StringWriter();
			
			transformer.transform(new DOMSource(document), new StreamResult(sw));
			String bib = sw.toString();
			almaPut("bibs/" + mmsId, bib);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	public Bib getBib(String mmsId) {
		
		String resp = almaGet("bibs/" + mmsId);
		
		Map<String, List<String>> dc = new HashMap<String, List<String>>();
		
		try {
			InputSource source = new InputSource(new StringReader(resp));
	
			Document document= DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(source);
	
			XPath xpath = XPathFactory.newInstance().newXPath();

			Node fields = (Node) xpath.evaluate("/bib/record", 
					document, XPathConstants.NODE);
			
			// Add all fields to DC array
			for (int i = 0; i < fields.getChildNodes().getLength(); i++) {
				Node field = fields.getChildNodes().item(i);
				String fieldName = field.getNodeName().replace("dc:", "");
				if (dc.containsKey(fieldName)) {
					ArrayList<String> values = new ArrayList<String>(dc.get(fieldName));
					values.add(field.getTextContent());
					dc.put(fieldName, values);
				}
				else
					dc.put(fieldName, Arrays.asList(field.getTextContent()));
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		Bib bib = new Bib(mmsId, dc);
		return bib;
	}
	
	public void addBibToCollection(String mmsId, String collectionId) {
		almaPost("bibs/collections/" + collectionId + "/bibs",
				"<bib><mms_id>" + mmsId + "</mms_id></bib>");
		
	}
	
	/*******************
	 * Deposit 
	********************/
	
	public UserDeposit getDeposit(String userId, String depositId) {
		UserDeposit deposit;
		String resp = almaGet("users/" + userId + "/deposits/" + depositId);
		
    	deposit = JAXB.unmarshal(new StringReader(resp), UserDeposit.class);
    	return deposit;
	}
	
	public ArrayList<String> getDepositFiles(UserDeposit deposit) {
		String resp = almaGet("bibs/" + deposit.getMmsId() + "/representations/" +
						deposit.getRepId() + "/files");
    	RepresentationFiles repFiles = 
    			JAXB.unmarshal(new StringReader(resp), RepresentationFiles.class);
    	
		ArrayList<String> files = new ArrayList<String>();
		String filename = "";
		for (RepresentationFile file : repFiles.getRepresentationFile()) {
			String[] parts = file.getPath().split("/");
			filename = parts[parts.length-1];
			files.add(filename);
		}
		return files;
	}
	
	public UserDeposit createDeposit(String depositProfileId, String mmsId, 
			String repId, String title, boolean draft, String userId) {
		UserDeposit deposit = new UserDeposit();
		deposit.setDepositProfile(depositProfileId);
		deposit.setMmsId(mmsId);
		deposit.setRepId(repId);
		deposit.setTitle(title);
		
		StringWriter sw = new StringWriter();
		JAXB.marshal(deposit, sw);
		
		Map<String, String> params = new HashMap<String, String>();
		if (draft) params.put("draft", "true");
		String resp = almaPost("users/" + userId + "/deposits",	sw.toString(), params);
    	deposit = JAXB.unmarshal(new StringReader(resp), UserDeposit.class);
    	return deposit;
	}
	
	public void submitDeposit(String userId, String depositId) {
		
		// For some reason need to submit the deposit.
		// TODO: Remove when bug is fixed
		String resp = almaGet("users/" + userId + "/deposits/" + depositId);
    	UserDeposit deposit = JAXB.unmarshal(new StringReader(resp), UserDeposit.class);
    	deposit.setNotes(null);
		StringWriter sw = new StringWriter();
		JAXB.marshal(deposit, sw);
    	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("op", "submit");
		resp = almaPost("users/" + userId + "/deposits/" + depositId, sw.toString(), params);
	}
	
	public void withdrawDeposit(String userId, String depositId) {
		// For some reason need to submit the deposit.
		// TODO: Remove when bug is fixed
		String resp = almaGet("users/" + userId + "/deposits/" + depositId);
    	UserDeposit deposit = JAXB.unmarshal(new StringReader(resp), UserDeposit.class);
    	deposit.setNotes(null);
		StringWriter sw = new StringWriter();
		JAXB.marshal(deposit, sw);
    	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("op", "withdraw");
		resp = almaPost("users/" + userId + "/deposits/" + depositId, sw.toString(), params);		
	}
	
	public DepositProfile getDepositProfile(String depositProfileId) {
		DepositProfile depositProfile = new DepositProfile();
		String resp = almaGet("conf/deposit-profiles/" + depositProfileId);
    	depositProfile = JAXB.unmarshal(new StringReader(resp), DepositProfile.class);
    	return depositProfile;
	}
	
	public DepositProfiles getDepositProfiles() {
		
		DepositProfiles depositProfiles = new DepositProfiles();
		String resp = almaGet("conf/deposit-profiles");
		
		depositProfiles = JAXB.unmarshal(new StringReader(resp), DepositProfiles.class);
		
		return depositProfiles;
		
	}
	
	/*******************
	 * Representation
	********************/
	
	public Representation createRepresentation(String mmsId, String libraryId) {
		
		Representation rep = new Representation();
		rep.setIsRemote(false);
		Representation.Library library = new Representation.Library();
		library.setValue(libraryId);
		Representation.UsageType usageType = new Representation.UsageType();
		usageType.setValue("PRESERVATION_MASTER");
		rep.setLibrary(library);
		rep.setUsageType(usageType);
		
		StringWriter sw = new StringWriter();
		JAXB.marshal(rep, sw);
		
		String resp = almaPost("bibs/" + mmsId + "/representations",
				sw.toString());

    	rep = JAXB.unmarshal(new StringReader(resp), Representation.class);
    	return rep;
	}
	
	/*******************
	 * File 
	********************/
	
	public void deleteFile(String userId, String depositId, String fileName) {

		String resp = almaGet("users/" + userId + "/deposits/" + depositId);
		
    	UserDeposit deposit = JAXB.unmarshal(new StringReader(resp), UserDeposit.class);
    	
    	// TODO: TEMP GET REP
		resp = almaGet("bibs/" + deposit.getMmsId() + "/representations");
    	Representations reps = JAXB.unmarshal(new StringReader(resp), Representations.class);
    	
    	String repId = reps.getRepresentation().get(0).getId();
    	
    	// TODO: Remove
    	deposit.setRepId(repId);
    	
    	// Get files
		resp = almaGet("bibs/" + deposit.getMmsId() + "/representations/" +
						repId + "/files");
    	RepresentationFiles repFiles = 
    			JAXB.unmarshal(new StringReader(resp), RepresentationFiles.class);
    	
		String filename = "";
		String fileId = null;
		for (RepresentationFile file : repFiles.getRepresentationFile()) {
			String[] parts = file.getPath().split("/");
			filename = parts[parts.length-1];
			if (filename.equals(fileName))
				fileId = file.getPid();
		}
		
		// Delete file
		almaDelete("bibs/" + deposit.getMmsId() + "/representations/" +
						deposit.getRepId() + "/files/" +
						fileId);

	}
	
	public void addFileToRepresentation(String mmsId, String repId, String path) {
		RepresentationFile repFile = new RepresentationFile();
		repFile.setPath(path);
		
		StringWriter sw = new StringWriter();
		JAXB.marshal(repFile, sw);
		
		almaPost("bibs/"+ mmsId + "/representations/" + repId + "/files",
				sw.toString());
	}
	
	
	/*******************
	 * User
	********************/
	
	public Boolean testUser() {
		try {
	    	almaGet("users/operation/test");
	    	return true;
		} catch (BadRequestException e) {
			return false;
		}
	}
	
	public User getUser() {
		return getUser(_authCredentials.getOnBehalfOf());
	}
	
	public User getUser(String userId) {

		User user = null;
		try {
    		
			Map<String, String> params = new HashMap<String, String>();
    		params.put("view", "brief");
	    	String resp = almaGet("users/" + _authCredentials.getOnBehalfOf(), params);

	    	user = JAXB.unmarshal(new StringReader(resp), User.class);
	    	
	    	log.info("Found user: " + user.getPrimaryId());
    	} 
    	catch (BadRequestException e) 
    	{
    		String resp = e.getResponse().readEntity(String.class);
    		if (resp.contains("Invalid API Key"))
    			throw new NotAuthorizedException("API Key is not valid");
    		else
    			return null;
    	} 
		
		return user;
	}
	
	/*******************
	 * Private functions
	********************/
	
	private String almaGet(String path, Map<String, String> params) {
		return AlmaRestUtil.get(
    			_properties.getAlmaUrl(), 
    			path, 
    			_authCredentials.getPassword(), 
    			params);	
	}
	
	private String almaGet(String path) {
		return almaGet(path, null);
	}
	
	private String almaPost(String path, String data,  Map<String, String> params) {
		return AlmaRestUtil.post(
				_properties.getAlmaUrl(), 
				path,
				_authCredentials.getPassword(),
				params,
				data);
	}
	
	private String almaPost(String path, String data) {
		return almaPost(path, data, null);
	}
	
	private String almaPut(String path, String data,  Map<String, String> params) {
		return AlmaRestUtil.put(
				_properties.getAlmaUrl(), 
				path,
				_authCredentials.getPassword(),
				params,
				data);
	}
	
	private String almaPut(String path, String data) {
		return almaPut(path, data, null);
	}
	
	private void almaDelete(String path, Map<String, String> params) {
		// Handle params if needed
		AlmaRestUtil.delete(
				_properties.getAlmaUrl(), 
				path, 
				_authCredentials.getPassword()
			);
	}
	
	private void almaDelete(String path) {
		almaDelete(path, null);
	}
	
	/*******************
	 * Inner Classes 
	********************/

	class DCEntity {
		private String _name;
		private String _value;
		
		public DCEntity(String name, String value) {
			_name = name;
			_value = value;
		}
		
		public String getName() {
			return _name;
		}
		
		public String getValue() {
			return _value;
		}
	}
	
	class Bib {
		private Map<String, List<String>> _dc;
		private String _mmsId;
		
		public Bib(String mmsId, Map<String, List<String>> dc) {
			_mmsId = mmsId;
			_dc = dc;
		}
		
		public String getMmsId() {
			return _mmsId;
		}
		
		public Map<String, List<String>> getDc() {
			return _dc;
		}
		
	}
}
