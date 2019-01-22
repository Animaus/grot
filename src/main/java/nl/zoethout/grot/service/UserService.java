package nl.zoethout.grot.service;

import java.util.List;
<<<<<<< HEAD

import javax.servlet.http.HttpServletRequest;

=======
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

>>>>>>> develop/Grot.190119.1252
import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

<<<<<<< HEAD
public interface UserService {
=======
public interface UserService extends UserDetailsService {
>>>>>>> develop/Grot.190119.1252
	public void saveUser(User user);

	public void saveAddress(Address address);

	public User readUser(int userId);

	public User readUser(String userName);

	public Address readAddress(int userId);

<<<<<<< HEAD
	/**
	 * Opvragen specifieke groep
	 */
	public Role readRole(String roleName);

	/**
	 * Opvragen alle groepen
	 */
	public List<Role> readRoles();

=======
	public Role readRole(String roleName);

	public Role readRole(int userId);

	public List<Role> readRoles();

	public Set<Role> readRoles(int userId);

	public List<String> readRoleNames();

	public List<String> readRoleNames(int userId);

>>>>>>> develop/Grot.190119.1252
	public User loginUser(String userName, String password);

	public void setPrincipal(HttpServletRequest req, User usr);

	public List<User> listProfiles();
}