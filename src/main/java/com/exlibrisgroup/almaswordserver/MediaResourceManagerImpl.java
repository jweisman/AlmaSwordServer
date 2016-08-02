package com.exlibrisgroup.almaswordserver;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.swordapp.server.AuthCredentials;
import org.swordapp.server.Deposit;
import org.swordapp.server.DepositReceipt;
import org.swordapp.server.MediaResource;
import org.swordapp.server.MediaResourceManager;
import org.swordapp.server.SwordAuthException;
import org.swordapp.server.SwordConfiguration;
import org.swordapp.server.SwordError;
import org.swordapp.server.SwordServerException;
import org.swordapp.server.UriRegistry;

import com.exlibrisgroup.almarestmodels.userdeposits.UserDeposit;

public class MediaResourceManagerImpl implements MediaResourceManager {
	
    private static Logger log = Logger.getLogger(MediaResourceManager.class);

	@Override
	public DepositReceipt addResource(String uri, Deposit deposit, AuthCredentials auth, SwordConfiguration config)
			throws SwordError, SwordServerException, SwordAuthException {

		AlmaRepository alma = new AlmaRepository(auth);
		String depositId = SwordUtilities.getUrlPart(uri, -1);
		UserDeposit userDeposit = alma.getDeposit(auth.getOnBehalfOf(), depositId);

    	ArrayList<String> fileList = SwordUtilities.uploadDepositedFiles(
    			deposit.getFile(),
    			deposit.getPackaging().equals(UriRegistry.PACKAGE_SIMPLE_ZIP),
    			deposit.getFilename()
    			);
    	
    	// Add files to representation
    	for (String file : fileList) {
    		alma.addFileToRepresentation(userDeposit.getMmsId(), 
    				userDeposit.getRepId(), 
    				file);
    		log.info("Added file to rep: " + file);
    	}

    	
        return SwordUtilities.getDepositReceipt(SwordUtilities.getBaseUrl(uri), userDeposit, 
        		null, fileList);
	}

	@Override
	public void deleteMediaResource(String uri, AuthCredentials auth, SwordConfiguration config)
			throws SwordError, SwordServerException, SwordAuthException {
		
		AlmaRepository alma = new AlmaRepository(auth);
		String[] parts = SwordUtilities.getUrlParts(uri);
		String depositId = parts[parts.length-2];
		String fileName = parts[parts.length-1];
		
		alma.deleteFile(auth.getOnBehalfOf(), depositId, fileName);

	}

	@Override
	public MediaResource getMediaResourceRepresentation(String uri, Map<String, String> accept, AuthCredentials auth,
			SwordConfiguration config) throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DepositReceipt replaceMediaResource(String uri, Deposit deposit, AuthCredentials auth,
			SwordConfiguration config) throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

}
