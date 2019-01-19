package nl.zoethout.grot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

public class MyUnitTest {
	protected static final String ADM = "ROLE_ADMIN";
	protected static final String USR = "ROLE_USER";

	protected String prefix = "";

	protected void print(Object str) {
		System.out.println(prefix + str);
	}

	protected static User getAdmin() {
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

	protected static User getUser() {
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

	protected static User getDisabled() {
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

	private static void addRole(Set<Role> roles, String roleDesc, String roleName) {
		if (roles == null) {
			roles = new HashSet<Role>();
		}
		Role role = new Role();
		role.setRoleId(roles.size() + 1);
		role.setRoleDesc(roleDesc);
		role.setRoleName(roleName);
		roles.add(role);
	}

	private static Date getDate(String strDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
		}
		return date;
	}
}
