package nl.zoethout.grot.util;

import java.util.List;

import nl.zoethout.grot.domain.Member;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;

public interface AttributeProvider {
	public void setSAError(String error);
	public Principal getSAPrincipal();
	public void setSAPrincipal(Principal principal);
	public void setSAProfiles(List<Member> profiles);
//	public Member getSAMember();
//	public void setSAMember(Member member);
	// TODO 26 - Users - fieldvalidation - added
	public User getSAFixed();
	public void setSAFixed(User user);
	// TODO 26 - Users - fieldvalidation - mutable user
	public User getSAMutable();
	public void setSAMutable(User user);
}
