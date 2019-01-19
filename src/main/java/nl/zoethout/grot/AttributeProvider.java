package nl.zoethout.grot;

import nl.zoethout.grot.domain.WrappedUser;

public interface AttributeProvider {
	public WrappedUser getSAUser();

	public void setSAUser(WrappedUser usr);
}
