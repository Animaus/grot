package nl.zoethout.grot.dao;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

=======
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
>>>>>>> develop/Grot.190119.1252
import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
<<<<<<< HEAD

=======
>>>>>>> develop/Grot.190119.1252
	/**
	 * JTA-capable EntityManager is usually created by application server and
	 * obtained from it via JNDI.
	 */
<<<<<<< HEAD

=======
>>>>>>> develop/Grot.190119.1252
	private EntityManager em;

	@Autowired
	public void setEmf(EntityManagerFactory emf) {
		this.em = emf.createEntityManager();
	}

	@Override
	public void saveUser(User user) {
		em.merge(user);
		em.getTransaction().begin();
		em.getTransaction().commit();
	}

	@Override
	public void saveAddress(Address address) {
		em.merge(address);
		em.getTransaction().begin();
		em.getTransaction().commit();
	}

	@Override
<<<<<<< HEAD
	public User readUser(int userId) {
		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr WHERE usr.userId = :parUserId",
				User.class);
		query.setParameter("parUserId", userId);

		try {
			// User found
			User success = query.getSingleResult();
			return success;
=======
	public User readUser(final int userId) {
		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr WHERE usr.userId = :parUserId", User.class);
		query.setParameter("parUserId", userId);
		try {
			// User found
			User user = query.getSingleResult();
			Set<Role> roles = readRoles(user.getUserId());
			user.setRoles(roles);
			return user;
>>>>>>> develop/Grot.190119.1252
		} catch (NoResultException nre) {
			// No user found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
<<<<<<< HEAD

	}

	@Override
	public User readUser(String userName) {
	
		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr WHERE usr.userName = :parUserName",
				User.class);
		query.setParameter("parUserName", userName);
	
		try {
			// User found
			User success = query.getSingleResult();
			return success;
=======
	}

	@Override
	public User readUser(final String userName) {
		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr WHERE usr.userName = :parUserName",
				User.class);
		query.setParameter("parUserName", userName);
		try {
			// User found
			User user = query.getSingleResult();
			Set<Role> roles = readRoles(user.getUserId());
			user.setRoles(roles);
			return user;
>>>>>>> develop/Grot.190119.1252
		} catch (NoResultException nre) {
			// No user found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
<<<<<<< HEAD
	
	}

	@Override
	public Address readAddress(int userId) {
	
		TypedQuery<Address> query = em.createQuery("SELECT address FROM Address address WHERE address.userId = :parUserId",
				Address.class);
		query.setParameter("parUserId", userId);
	
		try {
			// User found
			Address success = query.getSingleResult();
			return success;
=======
	}

	@Override
	public Address readAddress(final int userId) {
		TypedQuery<Address> query = em
				.createQuery("SELECT address FROM Address address WHERE address.userId = :parUserId", Address.class);
		query.setParameter("parUserId", userId);
		try {
			// User found
			Address address = query.getSingleResult();
			return address;
>>>>>>> develop/Grot.190119.1252
		} catch (NoResultException nre) {
			// No Address found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
<<<<<<< HEAD
	
	}

	@Override
	public Role readRole(String roleName) {

=======
	}

	@Override
	public Role readRole(final String roleName) {
>>>>>>> develop/Grot.190119.1252
		// Doet een query in de database
		TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role WHERE role.roleName = :parRoleName",
				Role.class);
		query.setParameter("parRoleName", roleName);
<<<<<<< HEAD

=======
>>>>>>> develop/Grot.190119.1252
		// Controleert de query
		try {
			Role role = query.getSingleResult();
			return role;
		} catch (NoResultException failure) {
			return null;
		}
<<<<<<< HEAD

=======
	}

	@Override
	public Role readRole(int userId) {
		TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role WHERE role.roleId = :parUserId",
				Role.class);
		query.setParameter("parUserId", userId);
		try {
			Role role = query.getSingleResult();
			return role;
		} catch (NoResultException failure) {
			return null;
		}
>>>>>>> develop/Grot.190119.1252
	}

	@Override
	public List<Role> readRoles() {
<<<<<<< HEAD

		// Doet een query in de database
		TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role", Role.class);

=======
		// Doet een query in de database
		TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role", Role.class);
>>>>>>> develop/Grot.190119.1252
		// Controleert de query
		try {
			// WEL meervoudig resultaat
			List<Role> roles = query.getResultList();
			return roles;
		} catch (IndexOutOfBoundsException e) {
			// GEEN meervoudig resultaat
			return null;
		}
<<<<<<< HEAD

	}

	@Override
	public void executeSQL(String sql) {
	}

	@Override
	public void updateUser(String userName, String SQLColumn, String SQLValue) {
=======
	}

	@Override
	public Set<Role> readRoles(int userId) {
		Query query = em.createNativeQuery("SELECT role_id FROM user_role WHERE user_id = :parUserId");
		query.setParameter("parUserId", userId);
		try {
			@SuppressWarnings("unchecked")
			List<Integer> userRoles = query.getResultList();
			Set<Role> result = new TreeSet<Role>();
			for (int i : userRoles) {
				result.add(readRole(i));
			}
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<String> readRoleNames() {
		List<Role> roles = readRoles();
		return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
	}

	@Override
	public List<String> readRoleNames(int userId) {
		Set<Role> roles = readRoles(userId);
		return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
	}

	@Override
	public void executeSQL(final String sql) {
	}

	@Override
	public void updateUser(final String userName, final String SQLColumn, final String SQLValue) {
>>>>>>> develop/Grot.190119.1252
		return;
	}

	@Override
<<<<<<< HEAD
	public void deleteUser(String userName) {
	}

	@Override
	public List<String> listPropertiesSame(String pojoField, String pojoValue) {
=======
	public void deleteUser(final String userName) {
	}

	@Override
	public List<String> listPropertiesSame(final String pojoField, final String pojoValue) {
>>>>>>> develop/Grot.190119.1252
		String SQL = "select user." + pojoField + " from User user where user." + pojoField + " = :parPojoValue";
		List<String> result = listProperties(SQL, pojoValue, "");
		return result;
	}

	@Override
<<<<<<< HEAD
	public List<String> listPropertiesLike(String pojoField, String pojoValue) {
=======
	public List<String> listPropertiesLike(final String pojoField, final String pojoValue) {
>>>>>>> develop/Grot.190119.1252
		String SQL = "select user." + pojoField + " from User user where user." + pojoField + " like :parPojoValue";
		List<String> result = listProperties(SQL, pojoValue, "%");
		return result;
	}

<<<<<<< HEAD
	private List<String> listProperties(String SQL, String pojoValue, String strPercent) {

		TypedQuery<String> query = em.createQuery(SQL, String.class);
		query.setParameter("parPojoValue", pojoValue + strPercent);

		List<String> dummy = new ArrayList<String>();

=======
	private List<String> listProperties(final String SQL, final String pojoValue, final String strPercent) {
		TypedQuery<String> query = em.createQuery(SQL, String.class);
		query.setParameter("parPojoValue", pojoValue + strPercent);
		List<String> dummy = new ArrayList<String>();
>>>>>>> develop/Grot.190119.1252
		try {
			List<String> userNames = new ArrayList<String>();
			for (java.lang.Object objUserName : query.getResultList()) {
				String userName = (String) objUserName;
				userNames.add(userName);
			}
			if (userNames.size() == 0) {
				userNames = dummy;
			}
			return userNames;
		} catch (NoResultException e) {
			return dummy;
		}
<<<<<<< HEAD

	}

	@Override
	public User loginUser(String userName, String password) {

=======
	}

	@Override
	public User loginUser(final String userName, final String password) {
>>>>>>> develop/Grot.190119.1252
		TypedQuery<User> query = em.createQuery(
				"SELECT usr FROM User usr WHERE usr.enabled = true AND usr.userName = :parUserName AND usr.password = :parPassword",
				User.class);
		query.setParameter("parUserName", userName);
		query.setParameter("parPassword", password);
<<<<<<< HEAD

=======
>>>>>>> develop/Grot.190119.1252
		try {
			// User found
			User success = query.getSingleResult();
			return success;
		} catch (NoResultException nre) {
			// No user found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
<<<<<<< HEAD

=======
>>>>>>> develop/Grot.190119.1252
	}

	@Override
	public List<User> listProfiles() {
<<<<<<< HEAD

		List<User> profiles = new ArrayList<User>();

		String SQL = "SELECT user FROM User user";
		TypedQuery<User> query = em.createQuery(SQL, User.class);

=======
		List<User> profiles = new ArrayList<User>();
		String SQL = "SELECT user FROM User user";
		TypedQuery<User> query = em.createQuery(SQL, User.class);
>>>>>>> develop/Grot.190119.1252
		try {
			List<User> users = query.getResultList();
			for (User user : users) {
				profiles.add(user);
			}
		} catch (NoResultException e) {
			// Oeps...
		}
<<<<<<< HEAD

		return profiles;
	}

=======
		return profiles;
	}
>>>>>>> develop/Grot.190119.1252
}