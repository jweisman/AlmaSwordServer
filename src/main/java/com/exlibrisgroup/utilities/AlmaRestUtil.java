package com.exlibrisgroup.utilities;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;


public class AlmaRestUtil {
	
    // private static Logger log = Logger.getLogger(AlmaRestUtil.class);
	
	public static String get(String server, String path, String apikey) {
		return get(server, path, apikey, null, true);
	}
    
	public static String get(String server, String path, String apikey, Map<String, String> params) {
		return get(server, path, apikey, params, true);
	}
	
    public static String get(String server, String path, String apikey, Map<String, String> params, boolean xml) {
    	Client client = ClientBuilder.newBuilder()
    			.register(JacksonFeature.class)
    			.build();
    	WebTarget target = client.target(server).path(path);
    	target = addParams(target, params);
    	String resp = target.request(xml ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON)
    			.header("Authorization", "apikey " + apikey)
    			.get(String.class); 
    	return resp;
    }
    
    public static String post(String server, String path, String apikey, Map<String, String> params, 
    		String data, Boolean xml) {
    	Client client = ClientBuilder.newBuilder()
    			.register(JacksonFeature.class)
    			.build();
    	WebTarget target = client.target(server).path(path);
    	target = addParams(target, params);
    	String resp;
    	if (xml) {
    		resp = target.request(MediaType.APPLICATION_XML)
        			.header("Authorization", "apikey " + apikey)
        			.post(Entity.xml(data)).readEntity(String.class);
    	} else { 
    		resp = target.request(MediaType.APPLICATION_JSON)
        			.header("Authorization", "apikey " + apikey)
        			.post(Entity.json(data)).readEntity(String.class);
    	}
    	return resp;
    }
    
    public static String post(String server, String path, String apikey, Map<String, String> params, 
    		String data) {
    	return post(server, path, apikey, params, data, true);
    }
    
    public static String post(String server, String path, String apikey, String data) {
    	return post(server, path, apikey, null, data, true);
    }
    
    public static String put(String server, String path, String apikey, Map<String, String> params, 
    		String data, Boolean xml) {
    	Client client = ClientBuilder.newBuilder()
    			.register(JacksonFeature.class)
    			.build();
    	WebTarget target = client.target(server).path(path);
    	target = addParams(target, params);
    	String resp;
    	if (xml) {
    		resp = target.request(MediaType.APPLICATION_XML)
        			.header("Authorization", "apikey " + apikey)
        			.put(Entity.xml(data)).readEntity(String.class);
    	} else { 
    		resp = target.request(MediaType.APPLICATION_JSON)
        			.header("Authorization", "apikey " + apikey)
        			.put(Entity.json(data)).readEntity(String.class);
    	}
    	return resp;
    }
    
    public static String put(String server, String path, String apikey, Map<String, String> params, 
    		String data) {
    	return put(server, path, apikey, params, data, true);
    }
    
    public static String put(String server, String path, String apikey, String data) {
    	return put(server, path, apikey, null, data, true);
    }    
    
    public static void delete (String server, String path, String apikey) {
    	delete(server, path, apikey, true);
    }
    
    public static void delete(String server, String path, String apikey, boolean xml) {
    	Client client = ClientBuilder.newBuilder()
    			.register(JacksonFeature.class)
    			.build();
    	WebTarget target = client.target(server).path(path);
    	target.request(xml ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON)
    			.header("Authorization", "apikey " + apikey)
    			.delete(); 
    }
    
    private static WebTarget addParams(WebTarget target, Map<String, String> params) {
    	if (params == null) return target;
    	WebTarget result = target;
    	for (String key: params.keySet()) {		
    	  	String value = params.get(key);
    		result = result.queryParam(key, value);   
    	}
    	return result;
    }
    
    
}


