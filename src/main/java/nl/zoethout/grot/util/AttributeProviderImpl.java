package nl.zoethout.grot.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.zoethout.grot.domain.Member;
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
	public void setSAProfiles(List<Member> profiles) {
		ses.setAttribute(SessionAttributes.PROFILES, profiles);
	}

//	@Override
//	public Member getSAMember() {
//		return (Member) ses.getAttribute(SessionAttributes.MEMBER);
//	}
//
//	@Override
//	public void setSAMember(Member member) {
//		ses.setAttribute(SessionAttributes.MEMBER, member);
//	}

	@Override
	public User getSAFixed() {
		return (User) ses.getAttribute(SessionAttributes.FIXED);
	}

	@Override
	public void setSAFixed(User user) {
		ses.setAttribute(SessionAttributes.FIXED, user);
	}
	
	@Override
	// TODO 26 - Users - fieldvalidation - mutable user
	public User getSAMutable() {
		return (User) ses.getAttribute(SessionAttributes.MUTABLE);
	}

	@Override
	// TODO 26 - Users - fieldvalidation - mutable user
	public void setSAMutable(User user) {
		ses.setAttribute(SessionAttributes.MUTABLE, user);
	}

}
