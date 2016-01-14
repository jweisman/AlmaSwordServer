package com.exlibrisgroup.almaswordserver;

import java.io.IOException;
import java.nio.file.Files;

public class SwordConfiguration implements org.swordapp.server.SwordConfiguration {

    public boolean returnDepositReceipt()
    {
        return true;
    }

    public boolean returnStackTraceInError()
    {
        return true;
    }

    public boolean returnErrorBody()
    {
        return true;
    }

    public String generator()
    {
        return "http://alma-sword.ext.exlibrisgroup.com/";
    }

    public String generatorVersion()
    {
        return "2.0";
    }

    public String administratorEmail()
    {
        return null;
    }

	public String getAuthType()
	{
		return "Basic";
	}

	public boolean storeAndCheckBinary()
	{
		return true;
	}

	public String getTempDirectory()
	{
		try {
			return Files.createTempDirectory("SWORD").toString();
		} catch (IOException e) {
			return "";
		}
	}

	public int getMaxUploadSize()
	{
		return 262144000;
	}

    public String getAlternateUrl()
    {
        return null;
    }

    public String getAlternateUrlContentType()
    {
        return null;
    }

    public boolean allowUnauthenticatedMediaAccess()
    {
        return false;
    }

}
