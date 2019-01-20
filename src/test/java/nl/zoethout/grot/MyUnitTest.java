package nl.zoethout.grot;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.test.web.servlet.ResultActions;

import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

public class MyUnitTest {
	protected static final String ADM = "ROLE_ADMIN";
	protected static final String USR = "ROLE_USER";

	protected String prefix = "";

	protected void println(Object str) {
		System.out.println(prefix + str);
	}

	protected User getAdmin() {
		User usr = new User();
		Set<Role> roles = new HashSet<Role>();
		usr.setUserId(1);
		usr.setUserName("front00");
		usr.setFirstName("Terry");
		usr.setLastName("Fronte");
		usr.setPrefix("de la");
		usr.setSex("m");
		usr.setPassword("123456");
		usr.setEnabled(true);
		usr.setDateBirth(getDate("1118-06-17"));
		usr.setDateRegistered(getDate("2018-06-17"));
		addRole(roles, "Gebruiker", USR);
		addRole(roles, "Beheerder", ADM);
		usr.setRoles(roles);
		return usr;
	}

	protected User getUser() {
		User usr = new User();
		Set<Role> roles = new HashSet<Role>();
		usr.setUserId(2);
		usr.setUserName("arc0j00");
		usr.setFirstName("Jeanne");
		usr.setLastName("Arc");
		usr.setPrefix("d'");
		usr.setSex("f");
		usr.setPassword("123456");
		usr.setEnabled(true);
		usr.setDateBirth(getDate("1218-06-17"));
		usr.setDateRegistered(getDate("2018-06-17"));
		addRole(roles, "Gebruiker", USR);
		usr.setRoles(roles);
		return usr;
	}

	protected User getDisabled() {
		User usr = new User();
		Set<Role> roles = new HashSet<Role>();
		usr.setUserId(4);
		usr.setUserName("hawks00");
		usr.setFirstName("Stephen");
		usr.setLastName("Hawking");
		usr.setPrefix("");
		usr.setSex("m");
		usr.setPassword("123456");
		usr.setEnabled(false);
		usr.setDateBirth(getDate("1942-01-08"));
		usr.setDateRegistered(getDate("2018-11-23"));
		usr.setRoles(roles);
		return usr;
	}

	private void addRole(Set<Role> roles, String roleDesc, String roleName) {
		if (roles == null) {
			roles = new HashSet<Role>();
		}
		Role role = new Role();
		role.setRoleId(roles.size() + 1);
		role.setRoleDesc(roleDesc);
		role.setRoleName(roleName);
		roles.add(role);
	}

	protected void addRole(List<Role> roles, String roleDesc, String roleName) {
		if (roles == null) {
			roles = new ArrayList<Role>();
		}
		Role role = new Role();
		role.setRoleId(roles.size() + 1);
		role.setRoleDesc(roleDesc);
		role.setRoleName(roleName);
		roles.add(role);
	}

	private Date getDate(String strDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * Checks the contents of the sessionattributes
	 * 
	 * @param ra
	 * @throws Exception
	 */
	protected void checkAttributes(ResultActions ra) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("text");
		ra.andExpect(request().sessionAttribute("WELCOME", bundle.getString("WELCOME")));
		ra.andExpect(request().sessionAttribute("LOGIN_MSG", bundle.getString("LOGIN_MSG")));
		ra.andExpect(request().sessionAttribute("LOGIN_NON", bundle.getString("LOGIN_NON")));
		ra.andExpect(request().sessionAttribute("LOGIN_YES", bundle.getString("LOGIN_YES")));
		ra.andExpect(request().sessionAttribute("LOGIN_OUT", bundle.getString("LOGIN_OUT")));
		ra.andExpect(request().sessionAttribute("LOGIN_ONN", bundle.getString("LOGIN_ONN")));
		ra.andExpect(request().sessionAttribute("LOGIN_USR", bundle.getString("LOGIN_USR")));
		ra.andExpect(request().sessionAttribute("LOGIN_PWD", bundle.getString("LOGIN_PWD")));
		ra.andExpect(request().sessionAttribute("LOGIN_ERR", bundle.getString("LOGIN_ERR")));
		bundle = ResourceBundle.getBundle("link");
		ra.andExpect(request().sessionAttribute("LNK_USR_HOME", bundle.getString("LNK_USR_HOME")));
		ra.andExpect(request().sessionAttribute("LNK_USR_LOGIN", bundle.getString("LNK_USR_LOGIN")));
		ra.andExpect(request().sessionAttribute("LNK_USR_MEMBERS", bundle.getString("LNK_USR_MEMBERS")));
	}

	/*
	 * // TODO 31 - Testing roles from userService
	 * assertTrue(roles.size() == 6);
	 * when(userService.readRoles()).thenReturn(roles);
	 */
	protected List<Role> getRoles() {
		List<Role> roles = new ArrayList<Role>();
		addRole(roles, "admin", "Administrators");
		addRole(roles, "user", "Regular users");
		addRole(roles, "employee", "Employee");
		addRole(roles, "student", "Student");
		addRole(roles, "ROLE_USER", "Gebruiker");
		addRole(roles, "ROLE_ADMIN", "Beheerder");
		return roles;
	}
}
