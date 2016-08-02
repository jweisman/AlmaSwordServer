package com.exlibrisgroup.almaswordserver;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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

import com.exlibrisgroup.almarestmodels.depositprofiles.DepositProfile;
import com.exlibrisgroup.almarestmodels.representations.Representation;
import com.exlibrisgroup.almarestmodels.userdeposits.UserDeposit;

public class CollectionDepositManagerImpl implements CollectionDepositManager {
	
    private static Logger log = Logger.getLogger(CollectionDepositManager.class);

    //@Override
    public DepositReceipt createNew(String collectionUri, Deposit deposit, AuthCredentials authCredentials, SwordConfiguration config)
            throws SwordError, SwordServerException, SwordAuthException {
    	
    	LinkedHashMap<String, Long> timer = new LinkedHashMap<String, Long>();
    	timer.put("Start", System.nanoTime());
    	
    	SwordUtilities.Authenticate(authCredentials);
    	timer.put("Authenticate", System.nanoTime());
    	
    	UserDeposit userDeposit = null;
    	ArrayList<String> fileList = new ArrayList<String>();
    	
    	// Implement metadata only or file only, eventually
    	if (deposit.getSwordEntry() == null || deposit.getFile() == null) {
    		throw new SwordError(UriRegistry.ERROR_CONTENT, 
    				"Both metadata and content are required to create a deposit in this SWORD server");
    	}

    	try {
    		
    		AlmaRepository alma = new AlmaRepository(authCredentials);

    		// Create BIB
    		String mmsId = alma.createBib(deposit.getSwordEntry());
        	timer.put("Create BIB", System.nanoTime());
        	
        	// Get deposit profile information
        	String depositProfileId = collectionUri.substring(collectionUri.lastIndexOf('/') + 1);
        	DepositProfile depositProfile = alma.getDepositProfile(depositProfileId);
        	
    		// Add BIB to Collection
        	alma.addBibToCollection(mmsId, depositProfile.getCollectionAssignment().getValue());
        	timer.put("Add BIB to collection", System.nanoTime());
    		
    		// Create representation
        	Representation rep = alma.createRepresentation(mmsId, depositProfile.getLibrary().getValue());
        	String repId = rep.getId();
        	timer.put("Create Rep", System.nanoTime());
    		log.info("Created representation: " + repId);
    		
    		// Handle files
        	fileList = SwordUtilities.uploadDepositedFiles(
        			deposit.getFile(),
        			deposit.getPackaging().equals(UriRegistry.PACKAGE_SIMPLE_ZIP),
        			deposit.getFilename()
        			);

	    	timer.put("Upload Files", System.nanoTime());
        	
        	// Add files to representation
        	for (String file : fileList) {
        		alma.addFileToRepresentation(mmsId, repId, file);
        		log.info("Added file to rep: " + file);
        	}
        	
	    	timer.put("Add files to rep", System.nanoTime());
	    	
	        // Add deposit for user
	    	String title;
	    	if (deposit.getSwordEntry().getTitle() != null)
	    		title = deposit.getSwordEntry().getTitle();
	    	else
	    		title = deposit.getSwordEntry().getDublinCore().get("title").get(0);
	    	
	        userDeposit = alma.createDeposit(depositProfileId, mmsId, repId,
	        		title, deposit.isInProgress(), authCredentials.getOnBehalfOf());
	        log.info("Created deposit: " + userDeposit.getDepositId());
	        	        
        } catch (Exception e) {
        	log.error(e.getMessage());
			throw new SwordServerException(e);
        }

    	// Log times
    	log.info(SwordUtilities.getTimingString(timer));
        return SwordUtilities.getDepositReceipt(SwordUtilities.getBaseUrl(collectionUri), 
        		userDeposit, deposit.getSwordEntry().getDublinCore(), fileList);
       
    }
    
}