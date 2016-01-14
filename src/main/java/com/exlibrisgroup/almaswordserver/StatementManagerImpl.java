package com.exlibrisgroup.almaswordserver;

import java.util.Map;

import org.swordapp.server.AuthCredentials;
import org.swordapp.server.Statement;
import org.swordapp.server.StatementManager;
import org.swordapp.server.SwordAuthException;
import org.swordapp.server.SwordConfiguration;
import org.swordapp.server.SwordError;
import org.swordapp.server.SwordServerException;

public class StatementManagerImpl implements StatementManager {

	@Override
	public Statement getStatement(String iri, Map<String, String> accept, AuthCredentials auth,
			SwordConfiguration config) throws SwordServerException, SwordError, SwordAuthException {
		// TODO Auto-generated method stub
		return null;
	}

}
