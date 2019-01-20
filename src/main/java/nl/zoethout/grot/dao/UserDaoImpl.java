package nl.zoethout.grot.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	/**
	 * JTA-capable EntityManager is usually created by application server and
	 * obtained from it via JNDI.
	 */

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
	public User readUser(int userId) {
		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr WHERE usr.userId = :parUserId",
				User.class);
		query.setParameter("parUserId", userId);

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
		} catch (NoResultException nre) {
			// No user found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
	
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
		} catch (NoResultException nre) {
			// No Address found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
	
	}

	@Override
	public Role readRole(String roleName) {

		// Doet een query in de database
		TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role WHERE role.roleName = :parRoleName",
				Role.class);
		query.setParameter("parRoleName", roleName);

		// Controleert de query
		try {
			Role role = query.getSingleResult();
			return role;
		} catch (NoResultException failure) {
			return null;
		}

	}

	@Override
	public List<Role> readRoles() {

		// Doet een query in de database
		TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role", Role.class);

		// Controleert de query
		try {
			// WEL meervoudig resultaat
			List<Role> roles = query.getResultList();
			return roles;
		} catch (IndexOutOfBoundsException e) {
			// GEEN meervoudig resultaat
			return null;
		}

	}

	@Override
	public void executeSQL(String sql) {
	}

	@Override
	public void updateUser(String userName, String SQLColumn, String SQLValue) {
		return;
	}

	@Override
	public void deleteUser(String userName) {
	}

	@Override
	public List<String> listPropertiesSame(String pojoField, String pojoValue) {
		String SQL = "select user." + pojoField + " from User user where user." + pojoField + " = :parPojoValue";
		List<String> result = listProperties(SQL, pojoValue, "");
		return result;
	}

	@Override
	public List<String> listPropertiesLike(String pojoField, String pojoValue) {
		String SQL = "select user." + pojoField + " from User user where user." + pojoField + " like :parPojoValue";
		List<String> result = listProperties(SQL, pojoValue, "%");
		return result;
	}

	private List<String> listProperties(String SQL, String pojoValue, String strPercent) {

		TypedQuery<String> query = em.createQuery(SQL, String.class);
		query.setParameter("parPojoValue", pojoValue + strPercent);

		List<String> dummy = new ArrayList<String>();

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

	}

	@Override
	public User loginUser(String userName, String password) {

		TypedQuery<User> query = em.createQuery(
				"SELECT usr FROM User usr WHERE usr.enabled = true AND usr.userName = :parUserName AND usr.password = :parPassword",
				User.class);
		query.setParameter("parUserName", userName);
		query.setParameter("parPassword", password);

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

	}

	@Override
	public List<User> listProfiles() {

		List<User> profiles = new ArrayList<User>();

		String SQL = "SELECT user FROM User user";
		TypedQuery<User> query = em.createQuery(SQL, User.class);

		try {
			List<User> users = query.getResultList();
			for (User user : users) {
				profiles.add(user);
			}
		} catch (NoResultException e) {
			// Oeps...
		}

		return profiles;
	}

}