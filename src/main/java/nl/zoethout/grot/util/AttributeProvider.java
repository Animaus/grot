package nl.zoethout.grot.util;

import java.util.List;

import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.User;

public interface AttributeProvider {
	public void setSAError(String error);
	public Principal getSAPrincipal();
	public void setSAPrincipal(Principal principal);
	public void setSAProfiles(List<User> profiles);
	public User getSAFixed();
	public void setSAFixed(User user);
	public void setSAUsername(String username);
}
