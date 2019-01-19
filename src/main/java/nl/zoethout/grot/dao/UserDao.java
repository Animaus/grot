package nl.zoethout.grot.dao;

import java.util.List;

import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

public interface UserDao {

	// http://www.tutorialspoint.com/spring/spring_jdbc_example.htm

	public void executeSQL(String sql);

	public void saveUser(User user);
	
	public void saveAddress(Address address);

	public void updateUser(String userName, String SQLColumn, String SQLValue);

	public User readUser(int userId);

	public User readUser(String userName);

	public Address readAddress(int userId);

	public void deleteUser(String userName);

	/**
	 * Identical fieldvalues
	 */
	public List<String> listPropertiesSame(String pojoField, String pojoValue);

	/**
	 * Similar fieldvalues
	 */
	public List<String> listPropertiesLike(String pojoField, String pojoValue);

	/**
	 * Specific role
	 */
	public Role readRole(String roleName);

	/**
	 * All roles
	 */
	public List<Role> readRoles();

	public User loginUser(String userName, String password);

	/**
	 * List of UserProfiles
	 */
	public List<User> listProfiles();

}
