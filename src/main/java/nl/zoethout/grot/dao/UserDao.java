package nl.zoethout.grot.dao;

import java.util.List;
import java.util.Set;

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

	public List<String> listPropertiesSame(String pojoField, String pojoValue);

	public List<String> listPropertiesLike(String pojoField, String pojoValue);

	public Role readRole(String roleName);

	public Role readRole(int userId);
	
	public List<Role> readRoles();

	public Set<Role> readRoles(int userId);

	public List<String> readRoleNames();

	public List<String> readRoleNames(int userId);

	public User loginUser(String userName, String password);

	public List<User> listProfiles();
}
