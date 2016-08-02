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
import org.swordapp.server.UriRegistry;

import com.exlibrisgroup.almarestmodels.userdeposits.UserDeposit;
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
		
		SwordUtilities.Authenticate(auth);
		
		AlmaRepository alma = new AlmaRepository(auth);
		String depositId = SwordUtilities.getUrlPart(editIRI, -1);
		
		UserDeposit userDeposit = alma.getDeposit(auth.getOnBehalfOf(), depositId);
		
		if (!allowEdit(userDeposit.getStatus()))
			throw new SwordError(UriRegistry.ERROR_BAD_REQUEST, "Deposit not in valid status for update.");

		alma.withdrawDeposit(auth.getOnBehalfOf(), depositId);
		
	}

	// GET /edit/deposit_id
	@Override
	public DepositReceipt getEntry(String editIRI, Map<String, String> accept, AuthCredentials auth,
			SwordConfiguration config) throws SwordServerException, SwordError, SwordAuthException {
		
		SwordUtilities.Authenticate(auth);
		
		AlmaRepository alma = new AlmaRepository(auth);
		String depositId = SwordUtilities.getUrlPart(editIRI, -1);
		
		UserDeposit userDeposit = alma.getDeposit(auth.getOnBehalfOf(), depositId);
		String mmsId = userDeposit.getMmsId();
		Bib bib = alma.getBib(mmsId);

		DepositReceipt receipt = SwordUtilities.getDepositReceipt(SwordUtilities.getBaseUrl(editIRI), 
				userDeposit, bib.getDc(), alma.getDepositFiles(userDeposit));
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
		String depositId = SwordUtilities.getUrlPart(editIRI, -1);
		
		UserDeposit userDeposit = alma.getDeposit(auth.getOnBehalfOf(), depositId);
		
		if (!allowEdit(userDeposit.getStatus()))
			throw new SwordError(UriRegistry.ERROR_BAD_REQUEST, "Deposit not in valid status for update.");

		alma.updateBib(userDeposit.getMmsId(), deposit.getSwordEntry().getDublinCore());
		
		if (!deposit.isInProgress()) {
			alma.submitDeposit(auth.getOnBehalfOf(), depositId);
		}

		return SwordUtilities.getDepositReceipt(SwordUtilities.getBaseUrl(editIRI), userDeposit, 
				deposit.getSwordEntry().getDublinCore(), alma.getDepositFiles(userDeposit));

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

	private boolean allowEdit(String status) {
		ArrayList<String> allowedStatuses = new ArrayList<String>();
		allowedStatuses.add("RETURNED");
		allowedStatuses.add("DRAFT");
		return allowedStatuses.contains(status);
	}
}
