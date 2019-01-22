package nl.zoethout.grot.dao;

import java.util.ArrayList;
import java.util.List;
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
	public User readUser(final int userId) {
		TypedQuery<User> query = em.createQuery("SELECT usr FROM User usr WHERE usr.userId = :parUserId", User.class);
		query.setParameter("parUserId", userId);
		try {
			// User found
			User user = query.getSingleResult();
			Set<Role> roles = readRoles(user.getUserId());
			user.setRoles(roles);
			return user;
		} catch (NoResultException nre) {
			// No user found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
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
		} catch (NoResultException nre) {
			// No user found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
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
		} catch (NoResultException nre) {
			// No Address found
			return null;
		} catch (IllegalArgumentException iae) {
			// Unexpected error
			return null;
		}
	}

	@Override
	public Role readRole(final String roleName) {
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
		return;
	}

	@Override
	public void deleteUser(final String userName) {
	}

	@Override
	public List<String> listPropertiesSame(final String pojoField, final String pojoValue) {
		String SQL = "select user." + pojoField + " from User user where user." + pojoField + " = :parPojoValue";
		List<String> result = listProperties(SQL, pojoValue, "");
		return result;
	}

	@Override
	public List<String> listPropertiesLike(final String pojoField, final String pojoValue) {
		String SQL = "select user." + pojoField + " from User user where user." + pojoField + " like :parPojoValue";
		List<String> result = listProperties(SQL, pojoValue, "%");
		return result;
	}

	private List<String> listProperties(final String SQL, final String pojoValue, final String strPercent) {
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
	public User loginUser(final String userName, final String password) {
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