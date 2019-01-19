package nl.zoethout.grot.dao;

import java.util.List;

import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

public interface UserDao {

	// http://www.tutorialspoint.com/spring/spring_jdbc_example.htm

	public void executeSQL(String sql);

	public void saveUser(User user);

	public void updateUser(String userName, String SQLColumn, String SQLValue);

	public User readUser(String userName);

	public void deleteUser(String userName);

	/**
	 * Opvragen identieke veldwaarden
	 */
	public List<String> listPropertiesSame(String pojoField, String pojoValue);

	/**
	 * Opvragen gelijkende veldwaarden
	 */
	public List<String> listPropertiesLike(String pojoField, String pojoValue);

	/**
	 * Opvragen specifieke groep
	 */
	public Role readRole(String groupName);

	/**
	 * Opvragen alle groepen
	 */
	public List<Role> readRoles();

	/**
	 * Opvragen namen gebruikersgroepen en selectie daarvan koppelen aan gebruiker
	 */
	public List<String> listUserRoles(int userId);

	public User loginUser(String userName, String password);

}
