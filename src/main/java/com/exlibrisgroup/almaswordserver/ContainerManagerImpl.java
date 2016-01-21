package com.exlibrisgroup.almaswordserver;

import java.util.ArrayList;
import java.util.Map;

import org.swordapp.server.AuthCredentials;
import org.swordapp.server.ContainerManager;
import org.swordapp.server.Deposit;
import org.swordapp.server.DepositReceipt;
import org.swordapp.server.SwordAuthException;
import org.swordapp.server.SwordConfiguration;
import org.swordapp.server.SwordError;
import org.swordapp.server.SwordServerException;

import com.exlibrisgroup.almaswordserver.AlmaRepository.Bib;

public class ContainerManagerImpl implements ContainerManager {

	@Override
	public DepositReceipt addMetadata(String editIRI, Deposit deposit, AuthCredentials auth, SwordConfiguration config)
			throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DepositReceipt addMetadataAndResources(String editIRI, Deposit deposit, AuthCredentials auth,
			SwordConfiguration config) throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DepositReceipt addResources(String editIRI, Deposit deposit, AuthCredentials auth, SwordConfiguration config)
			throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteContainer(String editIRI, AuthCredentials auth, SwordConfiguration config)
			throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		
	}

	// GET /edit/deposit_id
	@Override
	public DepositReceipt getEntry(String editIRI, Map<String, String> accept, AuthCredentials auth,
			SwordConfiguration config) throws SwordServerException, SwordError, SwordAuthException {
		
		SwordUtilities.Authenticate(auth);
		
		AlmaRepository alma = new AlmaRepository(auth);
		String mmsId = SwordUtilities.getUrlPart(editIRI, -1);
		
		Bib bib = alma.getBib(mmsId);
		com.exlibrisgroup.almaswordserver.AlmaRepository.Deposit dep = alma.getDeposit(bib.getOrigId());

		DepositReceipt receipt = SwordUtilities.getDepositReceipt(SwordUtilities.getBaseUrl(editIRI), mmsId, 
				bib.getDc(), dep.getFiles());
		return receipt;

	}

	@Override
	public boolean isStatementRequest(String editIRI, Map<String, String> accept, AuthCredentials auth,
			SwordConfiguration config) throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return false;
	}

	// POST /edit/deposit_id
	@Override
	public DepositReceipt replaceMetadata(String editIRI, Deposit deposit, AuthCredentials auth,
			SwordConfiguration config) throws SwordError, SwordServerException, SwordAuthException {

		SwordUtilities.Authenticate(auth);
		
		AlmaRepository alma = new AlmaRepository(auth);
		String mmsId = SwordUtilities.getUrlPart(editIRI, -1);
		
		alma.updateBib(mmsId, deposit.getSwordEntry().getDublinCore());

		return SwordUtilities.getDepositReceipt(SwordUtilities.getBaseUrl(editIRI), mmsId, 
				deposit.getSwordEntry().getDublinCore(), new ArrayList<String>());
	
	}

	@Override
	public DepositReceipt replaceMetadataAndMediaResource(String editIRI, Deposit deposit, AuthCredentials auth,
			SwordConfiguration config) throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DepositReceipt useHeaders(String editIRI, Deposit deposit, AuthCredentials auth, SwordConfiguration config)
			throws SwordError, SwordServerException, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

}
