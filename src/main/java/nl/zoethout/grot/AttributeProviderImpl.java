package nl.zoethout.grot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.zoethout.grot.domain.WrappedUser;

public class AttributeProviderImpl implements AttributeProvider {

	private HttpSession ses;
	private static AttributeProviderImpl provider;

	private AttributeProviderImpl() {
	}

	private AttributeProviderImpl(HttpServletRequest req) {
		this.ses = req.getSession();
	}

	public static AttributeProviderImpl getProvider(HttpServletRequest req) {
		HttpSession session = req.getSession();
		provider = (AttributeProviderImpl) session
				.getAttribute(SessionAttributes.PROVIDER);
		if (provider == null) {
			synchronized (AttributeProviderImpl.class) {
				provider = new AttributeProviderImpl(req);
				session.setAttribute(SessionAttributes.PROVIDER, provider);
			}
		}
		return provider;
	}

	public WrappedUser getSAUser() {
		return (WrappedUser) ses.getAttribute(SessionAttributes.USER);
	}

	public void setSAUser(WrappedUser usr) {
		ses.setAttribute(SessionAttributes.USER, usr);
	}

}
