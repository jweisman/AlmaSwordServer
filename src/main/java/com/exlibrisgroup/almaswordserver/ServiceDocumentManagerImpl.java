package com.exlibrisgroup.almaswordserver;

import java.util.Arrays;
import org.swordapp.server.AuthCredentials;
import org.swordapp.server.ServiceDocument;
import org.swordapp.server.ServiceDocumentManager;
import org.swordapp.server.SwordAuthException;
import org.swordapp.server.SwordCollection;
import org.swordapp.server.SwordConfiguration;
import org.swordapp.server.SwordError;
import org.swordapp.server.SwordServerException;
import org.swordapp.server.SwordWorkspace;
import org.swordapp.server.UriRegistry;

import com.exlibrisgroup.almarestmodels.depositprofiles.DepositProfile;
import com.exlibrisgroup.almarestmodels.depositprofiles.DepositProfiles;
import com.exlibrisgroup.almarestmodels.depositprofiles.UserGroup;
import com.exlibrisgroup.almarestmodels.user.User;


public class ServiceDocumentManagerImpl implements ServiceDocumentManager {

    //@Override
    public ServiceDocument getServiceDocument(String sdUri, AuthCredentials authCredentials, 
    		SwordConfiguration config)
            throws SwordError, SwordServerException, SwordAuthException {
    	
     	User user = SwordUtilities.Authenticate(authCredentials, true);
    	AlmaRepository alma = new AlmaRepository(authCredentials);
    	DepositProfiles depositProfiles = alma.getDepositProfiles();
    	
        ServiceDocument service = new ServiceDocument();
        service.setMaxUploadSize(config.getMaxUploadSize());
        SwordWorkspace workspace = new SwordWorkspace();
        workspace.setTitle("Alma SWORD");
        for (DepositProfile dp : depositProfiles.getDepositProfiles()) {
        	boolean validDepositProfile = false;
        	for (UserGroup ug : dp.getUserGroups().getUserGroups()) {
        		if (ug.getValue().equals(user.getUserGroup().getValue()))
        			validDepositProfile = true;
        	}
        	if (validDepositProfile) {
	            SwordCollection swordCollection = new SwordCollection();
	            String id = dp.getId();
	            swordCollection.setHref(SwordUtilities.getBaseUrl(sdUri) + "/col/" + id);
	            swordCollection.setTitle(dp.getName());
	            swordCollection.setAccept("*/*");
	            swordCollection.setMediation(true);
	            swordCollection.setCollectionPolicy(dp.getInstructions());
	            swordCollection.setAcceptPackaging(Arrays.asList(UriRegistry.PACKAGE_BINARY, UriRegistry.PACKAGE_SIMPLE_ZIP));
	            workspace.addCollection(swordCollection);        	
        	}
        }
        service.addWorkspace(workspace);
        return service;

    }
}
