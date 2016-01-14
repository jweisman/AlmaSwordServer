package com.exlibrisgroup.almaswordserver;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.swordapp.server.AuthCredentials;
import org.swordapp.server.CollectionDepositManager;
import org.swordapp.server.Deposit;
import org.swordapp.server.DepositReceipt;
import org.swordapp.server.SwordAuthException;
import org.swordapp.server.SwordConfiguration;
import org.swordapp.server.SwordError;
import org.swordapp.server.SwordServerException;
import org.swordapp.server.UriRegistry;

public class CollectionDepositManagerImpl implements CollectionDepositManager {
	
    private static Logger log = Logger.getLogger(CollectionDepositManager.class);

    //@Override
    public DepositReceipt createNew(String collectionUri, Deposit deposit, AuthCredentials authCredentials, SwordConfiguration config)
            throws SwordError, SwordServerException, SwordAuthException {
    	
    	LinkedHashMap<String, Long> timer = new LinkedHashMap<String, Long>();
    	timer.put("Start", System.nanoTime());
    	
    	SwordUtilities.Authenticate(authCredentials);
    	timer.put("Authenticate", System.nanoTime());
    	
    	String depositId = null;
    	List<String> fileList = new ArrayList<String>();
    	
    	// Implement metadata only or file only, eventually
    	if (deposit.getSwordEntry() == null || deposit.getFile() == null) {
    		throw new SwordError(UriRegistry.ERROR_CONTENT, 
    				"Both metadata and content are required to create a deposit in this SWORD server");
    	}

    	try {
    		
    		AlmaRepository alma = new AlmaRepository(authCredentials);

    		// TODO: Remove original ID
    		String origId = Paths.get(deposit.getFile().getParent()).getFileName().toString();

    		// Create BIB
    		String mmsId = alma.createBib(deposit.getSwordEntry(), origId);
    		depositId = mmsId;
        	timer.put("Create BIB", System.nanoTime());
        	
    		// Add BIB to Collection
    		// TODO: Add BIB to Collection
    		
    		// Create representation
    		String repId = alma.createRepresentation(mmsId, origId);
        	timer.put("Create Rep", System.nanoTime());
    		log.info("Created representation: " + repId);
    		
    		// Handle files
        	fileList = SwordUtilities.uploadDepositedFiles(
        			deposit.getFile(),
        			deposit.getPackaging().equals(UriRegistry.PACKAGE_SIMPLE_ZIP),
        			deposit.getFilename()
        			);
        	
        	// TODO: Add file to representation
        	
	    	timer.put("Upload Files", System.nanoTime());
	    	
	        // Add deposit for user
	        // TODO: create deposit
	        // String depositId = alma.createDeposit(mmsId, repId);
	        

        } catch (Exception e) {
        	log.error(e.getMessage());
			throw new SwordServerException(e);
        }

    	// Log times
    	log.info(SwordUtilities.getTimingString(timer));
    	
        return SwordUtilities.getDepositReceipt(SwordUtilities.getBaseUrl(collectionUri), depositId, 
        		deposit.getSwordEntry().getDublinCore(), fileList);
       
    }
    
}