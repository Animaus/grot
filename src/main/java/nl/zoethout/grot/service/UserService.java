package nl.zoethout.grot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

public interface UserService {
	public void saveUser(User user);

	public User readUser(String userName);

	/**
	 * Opvragen specifieke groep
	 */
	public Role readRole(String roleName);

	/**
	 * Opvragen alle groepen
	 */
	public List<Role> readRoles();
	
	public User loginUser(String userName, String password);

	public void setGenialUser(HttpServletRequest req, User usr);
}