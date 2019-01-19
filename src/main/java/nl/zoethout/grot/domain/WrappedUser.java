package nl.zoethout.grot.domain;

import java.util.Date;
import java.util.List;

public class WrappedUser {

	// 1 - a private static variable to store the instance (usually final, but here it's not)
	// Store one instance (this is the singleton)
	private static WrappedUser USER ; // Lazy initialization: create on first use
	private User user;
	private List<String> roles;

	// 2 - a private constructor (no callers can instantiate directly)
	private WrappedUser(User usr, List<String> roles) {
		this.user = usr;
		this.roles = roles;
	}

	// 3 - public static method for callers to get a reference to the instance
	public static WrappedUser getUser(User usr, List<String> roles) {
		if (USER == null) {
			synchronized (WrappedUser.class) {
				if (USER == null) {
					USER = new WrappedUser(usr, roles);
				}
			}
		}
		return USER;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean hasRole(String role) {
		boolean result = false;
		for (String userRole : roles) {
			if (userRole.equals(role)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public Integer getUserId() {
		return user.getUserId();
	}

	public void setUserId(Integer userId) {
		user.setUserId(userId);
	}

	public String getName() {
		return user.getName();
	}

	public void setName(String name) {
		user.setName(name);
	}

	public String getEmail() {
		return user.getEmail();
	}

	public void setEmail(String email) {
		user.getEmail();
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}

	public boolean isEnabled() {
		return user.isEnabled();
	}

	public void setEnabled(boolean enabled) {
		user.setEnabled(enabled);
	}

	public Date getDateBirth() {
		return user.getDateBirth();
	}

	public void setDateBirth(Date dateBirth) {
		user.setDateBirth(dateBirth);
	}

	public Date getDateRegistered() {
		return user.getDateRegistered();
	}

	public void setDateRegistered(Date dateRegistered) {
		user.setDateRegistered(dateRegistered);
	}

}
