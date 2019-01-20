package nl.zoethout.grot.mytest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

public interface MyTestUsers extends MyTest {

	default User getAdmin() {
		// User
		User usr = new User();
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
		// Roles
		Set<Role> roles = new HashSet<Role>();
		addRole(roles, "Gebruiker", "ROLE_USER");
		addRole(roles, "Beheerder", "ROLE_ADMIN");
		usr.setRoles(roles);
		// Address
		Address address = new Address();
		address.setUserId(1);
		address.setUserName("front00");
		address.setStreetName("Forest");
		address.setStreetNumber("321");
		address.setZip("1013 TF");
		address.setCity("Amiens");
		address.setCountry("FR");
		address.setPhone1("0612345678");
		address.setPhone2("");
		address.setEmail1("thierry@domain.org");
		address.setEmail2("");
		usr.setAddress(address);
		// Return
		return usr;
	}

	default User getUser() {
		// User
		User usr = new User();
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
		// Roles
		Set<Role> roles = new HashSet<Role>();
		addRole(roles, "Gebruiker", "ROLE_USER");
		usr.setRoles(roles);
		// Address
		Address address = new Address();
		address.setUserId(2);
		address.setUserName("arc0j00");
		address.setStreetName("Convent");
		address.setStreetNumber("321 b");
		address.setZip("1413 JA");
		address.setCity("Orleans");
		address.setCountry("FR");
		address.setPhone1("0687654321");
		address.setPhone2("");
		address.setEmail1("jeanne@domain.org");
		address.setEmail2("");
		usr.setAddress(address);
		// Return
		return usr;
	}

	default User getDisabled() {
		// User
		User usr = new User();
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
		// Roles
		Set<Role> roles = new HashSet<Role>();
		usr.setRoles(roles);
		// Address
		Address address = new Address();
		address.setUserId(4);
		address.setUserName("hawks00");
		address.setStreetName("University Lane");
		address.setStreetNumber("99");
		address.setZip("9999 XX");
		address.setCity("Oxford");
		address.setCountry("GB");
		address.setPhone1("");
		address.setPhone2("");
		address.setEmail1("stephen@domain.org");
		address.setEmail2("");
		usr.setAddress(address);
		// Return
		return usr;
	}

	default Date getDate(final String strDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
		}
		return date;
	}

	default void addRole(Set<Role> roles, final String roleDesc, final String roleName) {
		if (roles == null) {
			roles = new HashSet<Role>();
		}
		Role role = new Role();
		role.setRoleId(roles.size() + 1);
		role.setRoleDesc(roleDesc);
		role.setRoleName(roleName);
		roles.add(role);
	}

}
