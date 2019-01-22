package nl.zoethout.grot.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;

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

	@Override
	public void setSAError(String error) {
		ses.setAttribute(SessionAttributes.ERROR, error);
	}

	@Override
	public Principal getSAPrincipal() {
		return (Principal) ses.getAttribute(SessionAttributes.USER);
	}

	@Override
	public void setSAPrincipal(Principal usr) {
		ses.setAttribute(SessionAttributes.USER, usr);
	}

	@Override
	public void setSAProfiles(List<User> profiles) {
		ses.setAttribute(SessionAttributes.PROFILES, profiles);
	}

	@Override
	public User getSAFixed() {
		return (User) ses.getAttribute(SessionAttributes.FIXED);
	}

	@Override
	public void setSAFixed(User user) {
		ses.setAttribute(SessionAttributes.FIXED, user);
	}
<<<<<<< HEAD
=======

	@Override
	public void setSAUsername(String username) {
		ses.setAttribute(SessionAttributes.USERNAME, username);
	}
>>>>>>> develop/Grot.190119.1252
}
