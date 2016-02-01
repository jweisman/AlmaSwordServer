package com.exlibrisgroup.almaswordserver;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
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
import javax.xml.parsers.DocumentBuilder;
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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.exlibrisgroup.almarestmodels.representation.Representation;
import com.exlibrisgroup.almarestmodels.user.User;
import com.exlibrisgroup.almaswordserver.SwordUtilities.AlmaProperties;
import com.exlibrisgroup.utilities.AlmaRestUtil;
import com.exlibrisgroup.utilities.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    
	public String createBib(SwordEntry entry, String origId) throws Exception {

		String xml = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + 
		"<bib>" +
		"	<mms_id></mms_id>" +
		"	<suppress_from_publishing>true</suppress_from_publishing>" +
		"	<record>" +
	    "        <leader>     aas          a     </leader>" +
	    "        <controlfield tag=\"008\">       " + new SimpleDateFormat("yyyy").format(new Date()) + "</controlfield>" +
	    "        <datafield tag=\"035\" ind1=\" \" ind2=\" \">" +
	    "          <subfield code=\"a\">(SWORD)" + origId + "</subfield>" +
	    "        </datafield>" +	    
	    "        <datafield tag=\"100\" ind1=\"1\" ind2=\" \">" +
	    "          <subfield code=\"a\">" + entry.getDublinCore().get("creator").get(0) + "</subfield>" +
	    "        </datafield>" +
	    "        <datafield tag=\"245\" ind1=\"1\" ind2=\"2\">" +
	    "          <subfield code=\"a\">" + entry.getDublinCore().get("title").get(0) + "</subfield>" +
	    "        </datafield>" +
	    "        <datafield tag=\"260\" ind1=\" \" ind2=\" \">" +
	    "          <subfield code=\"c\">" + new SimpleDateFormat("MMMM d, yyyy").format(new Date()) + "</subfield>" +
	    "        </datafield>" +
	    "	</record>" +
		"</bib>";
		
		String resp = AlmaRestUtil.post(
				_properties.getAlmaUrl(), 
				"bibs", 
				_authCredentials.getPassword(),
				null,
				xml,
				true
			);
		
		String mms_id = Util.find(resp, "<mms_id>(\\d*)<\\/mms_id>");
		if (mms_id == "") {
			log.error("BIB could not be created: " + resp);
			throw new Exception("Could not create BIB");
		}
		log.info("BIB Created: " + mms_id);
		return mms_id;
	}
	
	public void updateBib(String mmsId, Map<String, List<String>> dc) {

		String resp = AlmaRestUtil.get(
				_properties.getAlmaUrl(), 
				"bibs/" + mmsId, 
				_authCredentials.getPassword(),
				null,
				true
			);
		
		// TODO: Replace with DC handling
		
		try {
		
			InputSource source = new InputSource(new StringReader(resp));
			
			Document document= DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(source);
	
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			NodeList creator = (NodeList) xpath.evaluate("/bib/record/datafield[@tag='100']/subfield[@code='a']", 
					document, XPathConstants.NODESET);
			
			NodeList title = (NodeList) xpath.evaluate("/bib/record/datafield[@tag='245']/subfield[@code='a']", 
					document, XPathConstants.NODESET);
	
			creator.item(0).setTextContent(dc.get("creator").get(0));
			title.item(0).setTextContent(dc.get("title").get(0));
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StringWriter sw = new StringWriter();
			
			transformer.transform(new DOMSource(document), new StreamResult(sw));
			
			AlmaRestUtil.put(
					_properties.getAlmaUrl(), 
					"bibs/" + mmsId, 
					_authCredentials.getPassword(),
					null,
					sw.getBuffer().toString(),
					true
				);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	public Bib getBib(String mmsId) {
		
		String resp = AlmaRestUtil.get(
				_properties.getAlmaUrl(), 
				"bibs/" + mmsId, 
				_authCredentials.getPassword(),
				null,
				true
			);
		
		Map<String, List<String>> dc = new HashMap<String, List<String>>();
		String origId = "";
		
		try {
			// TODO: Replace with DC handling
			InputSource source = new InputSource(new StringReader(resp));
	
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(source);
	
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
	
			String creator = xpath.evaluate("/bib/record/datafield[@tag='100']/subfield[@code='a']", document);
			String title = xpath.evaluate("/bib/record/datafield[@tag='245']/subfield[@code='a']", document);
			origId = xpath.evaluate("/bib/record/datafield[@tag='035']/subfield[@code='a']", document);
			origId = origId.substring(origId.indexOf("(SWORD)") + 7);
	
			dc.put("creator", Arrays.asList(creator));
			dc.put("title", Arrays.asList(title));
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		Bib bib = new Bib(mmsId, dc, origId);
		return bib;
	}
	
	public Deposit getDeposit(String pid) {
		ArrayList<String> keys = SwordUtilities.getFilesFromS3(pid);
		ArrayList<String> files = new ArrayList<String>();
		String filename = "";
		for (String key : keys) {
			String[] parts = key.split("/");
			filename = parts[parts.length-1];
			files.add(filename);
		}
		return new Deposit(files);
	}
	
	public String createRepresentation(String mmsId, String originatingRecord) {
		
		Representation rep = new Representation();
		Representation.Library library = new Representation.Library();
		library.setValue("MAIN");
		Representation.UsageType usageType = new Representation.UsageType();
		usageType.setValue("PRESERVATION_MASTER");
		Representation.Repository repository = new Representation.Repository();
		repository.setValue("AWS");
		rep.setLibrary(library);
		rep.setUsageType(usageType);
		rep.setRepository(repository);

		// Temporary for remote rep purposes
		rep.setLinkingParameter1(mmsId);
		rep.setIsRemote(true);
		rep.setOriginatingRecordId(originatingRecord);
		
		StringWriter sw = new StringWriter();
		JAXB.marshal(rep, sw);
		
		String resp = AlmaRestUtil.post(
				_properties.getAlmaUrl(), 
				"bibs/" + mmsId + "/representations",
				_authCredentials.getPassword(), 
				null,
				sw.toString(), 
				true
			);

    	rep = JAXB.unmarshal(new StringReader(resp), Representation.class);
    	return rep.getId();
	}
	
	
	public void deleteFile(String depositId, String fileId) {
		Bib bib = getBib(depositId);
		SwordUtilities.deleteFileFromS3(bib.getOrigId() + "/" + fileId);
	}
	
	@SuppressWarnings("unchecked")
	public List<DepositProfile> getDepositProfiles() {
		
    	HashMap<String, String> params = new HashMap<String, String>();
    	params.put("type", "REPOSITORY");
    	params.put("ie_type", "DIGITAL");
    	
		String resp = AlmaRestUtil.get(
				_properties.getAlmaUrl(),
    			"conf/md-import-profiles",
    			_authCredentials.getPassword(),
    			params);
		
		List<DepositProfile> list = new ArrayList<DepositProfile>();

		try {
			Gson gson = new Gson();
			Type obj = new TypeToken<Map<String, Object>>(){}.getType();
			Map<String, Object> map = gson.fromJson(resp, obj);
			ArrayList<Object> i = (ArrayList<Object>) map.get("import_profile");
			for (Object p : i) {
				Map<String, Object> profile = (Map<String, Object>) p;
				Map<String, Object> digitalDetails = 
						(Map<String, Object>) profile.get("digital_details");
				Map<String, Object> collection = 
						(Map<String, Object>) digitalDetails.get("collection_assignment");
				DepositProfile dp = new DepositProfile(
						(String) profile.get("id"), 
						(String) profile.get("name"), 
						(String) collection.get("desc"),
						(String) collection.get("value")
				);
				list.add(dp);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return list;
	}
	
	public Boolean testUser() {
		try {
	    	AlmaRestUtil.get(
	    			_properties.getAlmaUrl(), 
	    			"users/operation/test", 
	    			_authCredentials.getPassword());
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
	    	String resp = AlmaRestUtil.get(
	    			_properties.getAlmaUrl(), 
	    			"users/" + _authCredentials.getOnBehalfOf(), 
	    			_authCredentials.getPassword(), 
	    			params,
	    			true);	

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
	 * Inner Classes 
	********************/
	
	class DepositProfile {
		private String _id;
		private String _collectionName;
		private String _collectionId;
		private String _name;
		
		public DepositProfile(String id, String name, 
				String collectionName, String collectionId) {
			_id = id;
			_name = name;
			_collectionId = collectionId;
			_collectionName = collectionName;
		}
		
		public String getId() {
			return _id;
		}
		
		public String getCollectionId() {
			return _collectionId;
		}
		
		public String getCollectionName() {
			return _collectionName;
		}
		
		public String getName() {
			return _name;
		}
	}
	
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
		
		// TODO: Remove this
		private String _origId;
		
		public Bib(String mmsId, Map<String, List<String>> dc, String origId) {
			_mmsId = mmsId;
			_dc = dc;
			_origId = origId;
		}
		
		public String getMmsId() {
			return _mmsId;
		}
		
		public Map<String, List<String>> getDc() {
			return _dc;
		}
		
		public String getOrigId() {
			return _origId;
		}
	}
	
	class Deposit {
		private ArrayList<String> _files;
		
		public Deposit(ArrayList<String> files) {
			_files = files;
		}
		
		public ArrayList<String> getFiles() {
			return _files;
		}
	}
	
	class File {
		private String _url;
		private String _filename;
		
		public File(String url, String filename) {
			_url = url;
			_filename = filename;
		}
		
		public String getUrl() {
			return _url;
		}
		
		public String getFilename() {
			return _filename;
		}
		
		public void setUrl(String url) {
			_url = url;
		}
		
		public void setFilename(String filename) {
			_filename = filename;
		}
		
	}

}
