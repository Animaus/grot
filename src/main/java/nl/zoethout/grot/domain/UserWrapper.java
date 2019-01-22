package nl.zoethout.grot.domain;

import java.util.Date;
import java.util.Set;

public abstract class UserWrapper {

	protected User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Role> getUserRoles() {
		return user.getRoles();
	}

	public void setUserRoles(Set<Role> roles) {
		user.setRoles(roles);
	}

	public boolean hasRole(String roleName) {
		return getUserRoles().stream().map(Role::getRoleName).filter(roleName::equals).count() > 0;
	}

	public Integer getUserId() {
		return user.getUserId();
	}

	public void setUserId(Integer userId) {
		user.setUserId(userId);
	}

	public String getUserName() {
		return user.getUserName();
	}

	public void setUserName(String userName) {
		user.setUserName(userName);
	}

	public String getFirstName() {
		return user.getFirstName();
	}

	public void setFirstName(String firstName) {
		user.setFirstName(firstName);
	}

	public String getLastName() {
		return user.getLastName();
	}

	public void setLastName(String lastName) {
		user.setLastName(lastName);
	}

	public String getPrefix() {
		return user.getPrefix();
	}

	public void setPrefix(String prefix) {
		user.getPrefix();
	}

	public String getSex() {
		return user.getSex();
	}

	public void setSex(String sex) {
		user.getSex();
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
