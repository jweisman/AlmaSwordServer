package com.exlibrisgroup.almaswordserver;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.NotAuthorizedException;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.log4j.Logger;
import org.swordapp.server.AuthCredentials;
import org.swordapp.server.DepositReceipt;
import org.swordapp.server.SwordAuthException;
import org.swordapp.server.SwordError;
import org.swordapp.server.UriRegistry;

import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.exlibrisgroup.utilities.Util;

public class SwordUtilities {
	
    private static Logger log = Logger.getLogger(SwordUtilities.class);
    
    private static AlmaProperties _properties = null;
    private static Properties _props = getProperties();
    
    public static void Authenticate(AuthCredentials authCredentials) 
    		throws SwordAuthException {
    	Authenticate(authCredentials, false);
    }

    public static void Authenticate(AuthCredentials authCredentials, Boolean allowAnonymous) 
    		throws SwordAuthException {

    	AlmaRepository alma = new AlmaRepository(authCredentials);

    	if (authCredentials.getOnBehalfOf() == null) {
    		if (!allowAnonymous) {
    			throw new SwordAuthException("On-Behalf-Of user is is required.");
    		} else {
    			if (!alma.testUser()) 
        			throw new SwordAuthException("API Key is not valid");
    		} 
    	} else {
	    	try {
		    	com.exlibrisgroup.almarestmodels.User user = alma.getUser();
		    	if (user == null)
		    		throw new SwordAuthException("'On-Behalf-Of' user does not exist");
	    	} catch (NotAuthorizedException e) {
	    		throw new SwordAuthException("API Key is not valid");
	    	}
    	}
    }
    
    public static DepositReceipt getDepositReceipt(String rootUri, String depositId, 
    		Map<String, List<String>> dc, List<String> files) {
    	DepositReceipt receipt = new DepositReceipt();
    	
    	if (dc != null) {
	    	for (Map.Entry<String, List<String>> entry : dc.entrySet() ) {
	    		for (String value : entry.getValue()) { 
	    			receipt.addDublinCore(entry.getKey(), value);
	    		}
	    	}
    	}
    	
        IRI iri = new IRI(rootUri + "/edit/" + depositId);
        receipt.setLocation(iri);
        receipt.setEditIRI(iri);
        iri = new IRI(rootUri + "/edit-media/" + depositId);
        receipt.setEditMediaIRI(iri);
        receipt.setVerboseDescription(depositId);
        for (String file : files) {
        	String filename = null;
        	try {
        		filename = URLEncoder.encode(file, "UTF-8").replace("+", "%20");
			} catch (UnsupportedEncodingException e) {
				log.warn(e);
				filename = file;
			}
			receipt.addDerivedResource(rootUri + "/edit-media/" + depositId + "/" + filename, 
					URLConnection.guessContentTypeFromName(file));
        }

        return receipt;
    }
    
    private static void writeFileToS3(Path path, String folder) {
        AmazonS3 s3 = new AmazonS3Client();
    	s3.setRegion(_properties.getRegion());
    	String bucketName = _properties.getBucket();
    	String prefix = "" + _props.getProperty("prefix");
    	String key = prefix + folder + "/" + path.getFileName();
    	s3.putObject(new PutObjectRequest(bucketName, key, path.toFile()));    	
    }
    
    public static List<String> uploadDepositedFiles(File file, boolean zip, String filename) {
        Path path = Paths.get(file.getParent());

    	return uploadDepositedFiles(file, zip, filename, path.getFileName().toString());
    }
    
    public static List<String> uploadDepositedFiles(File file, boolean zip, String filename, String folder) {
    	List<String> fileList = new ArrayList<String>();
    	
    	try {
	        Path path = Paths.get(file.getParent());
	        
	        if (zip) {
	        	int files = Util.unzip(file, path);
	        	if (files == 0) {
	        		throw new SwordError(UriRegistry.ERROR_CONTENT, "File provided was not a zip");
	        	}
	        	file.delete();
	        } else { // http://purl.org/net/sword/package/Binary
	        	file.renameTo(new File(file.getParentFile(), filename));
	        }
	        
	        // Upload each file in the directory
	        Files.walk(path).forEach( filePath -> {
	        	if (!Files.isDirectory(filePath)) {
		        	log.info("Uploading file: " + filePath);
		        	writeFileToS3(filePath, folder);
		        	
		        	// Add file to list of files
		        	fileList.add(filePath.getFileName().toString());
	        	}
	        });
	        
	        // delete temp directory
	        Util.deleteDirectory(path);
	        
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    	}
    	return fileList;
    }
    
    public static ArrayList<String> getFilesFromS3(String id) {
    	ArrayList<String> files = new ArrayList<String>();
    	AmazonS3 s3 = new AmazonS3Client();
    	s3.setRegion(_properties.getRegion());
    	String bucketName = _properties.getBucket();
    	String prefix = _props.getProperty("prefix") + id + "/";
    	ObjectListing list = s3.listObjects(new ListObjectsRequest(bucketName, prefix, null, null, 100));
    	for (S3ObjectSummary obj : 
    		list.getObjectSummaries()) {
    		files.add(obj.getKey());
    	}
    	return files;
    }
    
    public static void deleteFileFromS3(String key) {
        AmazonS3 s3 = new AmazonS3Client();
    	s3.setRegion(_properties.getRegion());
    	String bucketName = _properties.getBucket();
    	String prefix = "" + _props.getProperty("prefix");
    	s3.deleteObject(bucketName, prefix + key);
    }
    
    public static String getSignedUrl(String key) {
    	AmazonS3 s3 = new AmazonS3Client();
    	s3.setRegion(_properties.getRegion());
    	String bucketName = _properties.getBucket();
    	
    	java.util.Date expiration = new java.util.Date();
    	long msec = expiration.getTime();
    	msec += 1000 * 60 * 60; // 1 hour.
    	expiration.setTime(msec);
    	             
    	GeneratePresignedUrlRequest generatePresignedUrlRequest = 
    	              new GeneratePresignedUrlRequest(bucketName, key);
    	generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
    	generatePresignedUrlRequest.setExpiration(expiration);
    	             
    	URL s = s3.generatePresignedUrl(generatePresignedUrlRequest); 
    	return s.toString();
    }
    
    public static Properties getProperties() 
    {
    	Properties prop = new Properties();
    	try {
	    	InputStream inputStream = null;
	    	try {
		    	String propFileName = "config.properties";
		    	inputStream = Util.class.getClassLoader().getResourceAsStream(propFileName);
				if (inputStream != null) {
					prop.load(inputStream);
				}
	    	} finally {
	    		inputStream.close();
	    	}
    	} catch (Exception e) {
    		log.warn("Property file could not be parsed");
    	}
    	return prop;
    }    
    
    public static String getBaseUrl(String url) {
    	
    	try
    	{
    		URL baseUrl = new URL(url);
    		String port = "";
    		if (baseUrl.getPort() != 443 && baseUrl.getPort() != 80 && baseUrl.getPort() > 0)
    			port = ":" + baseUrl.getPort();
    		return baseUrl.getProtocol() + "://" + baseUrl.getHost() + port;
    	}
    	catch (MalformedURLException e)
    	{
    	  return "";
    	}
    }
    
    public static String getTimingString(Map<String, Long> timer) {
    	String timing = "";
    	Iterator<Map.Entry<String, Long>> it = timer.entrySet().iterator();
    	long currentTime = it.next().getValue();
    	while (it.hasNext()) {
    		Map.Entry<String, Long> entry = it.next();
    		timing += entry.getKey() + ": " + ((entry.getValue() - currentTime) / 1000000) + " ms; ";
    		currentTime = entry.getValue();
    	}
    	
    	return timing;
    }
    
    public static String[] getUrlParts(String url) {
    	String[] parts = null;
    	try {
    		URI uri = new URI(url);
    		parts = uri.getPath().split("/");
    	} catch (Exception e) {
    		log.error(e.getMessage());
    	}
    	return parts;
    }
    
    public static String getUrlPart(String url, int position) {
    	String[] parts = getUrlParts(url);
    	if (position < 0) 
    		return parts[parts.length + position];
    	else
    		return parts[position];
    }
    
    public static AlmaProperties getAlmaProperties() {
    	if (_properties != null)
    		return _properties;
    	
    	AlmaProperties properties = new SwordUtilities().new AlmaProperties();
    	String region = getProperties().getProperty("region");
    	if (region == "eb") { // elastic beanstalk
    		// http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-instance-metadata.html#dynamic-data-categories
    		region = "na";
    	} else if (region == null) {
    		region = "na";
    	}
    	
    	switch (region) {
    	case "ap": 
    		properties.setAlmaUrl("https://api-ap.hosted.exlibrisgroup.com/almaws/v1");
    		properties.setBucket("ap-st01.ext.exlibrisgroup.com");
    		properties.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
    		break;
    	case "eu":
    		properties.setAlmaUrl("https://api-eu.hosted.exlibrisgroup.com/almaws/v1");
    		properties.setBucket("eu-st01.ext.exlibrisgroup.com");
    		properties.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
    		break;
    	case "test":
    		properties.setAlmaUrl("https://api-na.hosted.exlibrisgroup.com/almaws/v1");    		
    		properties.setBucket("almad-test");
    		properties.setRegion(Region.getRegion(Regions.US_EAST_1));
    		break;    		
    	default:
    		properties.setAlmaUrl("https://api-na.hosted.exlibrisgroup.com/almaws/v1");
    		properties.setBucket("na-st01.ext.exlibrisgroup.com");
    		properties.setRegion(Region.getRegion(Regions.US_EAST_1));
    		break;
    	}
    	
    	_properties = properties;
    	return properties;
    }
    
    class AlmaProperties {
    	String _almaUrl;
    	String _bucket;
    	Region _region;
    	
    	public String getAlmaUrl() {
    		return _almaUrl;
    	}
    	
    	public void setAlmaUrl(String almaUrl) {
    		_almaUrl = almaUrl;
    	}
    	
    	public String getBucket() {
    		return _bucket;
    	}
    	
    	public void setBucket(String bucket) {
    		_bucket = bucket;
    	}
    	
    	public Region getRegion() {
    		return _region;
    	}
    	
    	public void setRegion(Region region) {
    		_region = region;
    	}
    }
    
}
