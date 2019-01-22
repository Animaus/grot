package nl.zoethout.grot.security;

import javax.servlet.http.HttpServletRequest;

import nl.zoethout.grot.util.AttributeProvider;
import nl.zoethout.grot.util.AttributeProviderImpl;

public class Handler {
	protected String strClass = getClass().getSimpleName();

	protected AttributeProvider provider(final HttpServletRequest req) {
		AttributeProvider provider = AttributeProviderImpl.getProvider(req);
		return provider;
	}

	protected static void devInfo(String strClass, String strMethod, String strMessage) {
		System.out.println("TEST: (" + strClass + "-" + strMethod + ") " + strMessage);
	}
}
