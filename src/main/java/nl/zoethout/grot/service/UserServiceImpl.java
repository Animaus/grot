package nl.zoethout.grot.service;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
>>>>>>> develop/Grot.190119.1252

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
>>>>>>> develop/Grot.190119.1252
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.zoethout.grot.dao.UserDao;
import nl.zoethout.grot.domain.Address;
import nl.zoethout.grot.domain.Principal;
import nl.zoethout.grot.domain.Role;
import nl.zoethout.grot.domain.User;
import nl.zoethout.grot.util.AttributeProvider;
import nl.zoethout.grot.util.AttributeProviderImpl;

/*
 * This is the meta data that Spring will use when autowiring the eventService
 * property in the Controller created in the previous step. We've explicitly
 * defined the name 'userService' in the annotation's value, otherwise without
 * it Spring would have named the bean equal to the classname automatically and the
 * autowiring of this bean in the Controller and other classes would have
 * failed.
 */
@Service("userService")
/*
 * Annotation will be recognized by the tx:annotation-driven element in the
 * application context. Having placed the annotation at the class level means
 * that all methods in this class by default should adhere to these transaction
 * rules. "propagation = Propagation.SUPPORTS" means a transaction isn't
 * necessary, but if one exists, then the method will run in that transaction.
 * The readOnly=true attribute is pretty straight forward, by default all data
 * retrieved should be read only. No writes to the datasource are permitted.
 */
@Transactional
// (propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

	/*
	 * DAO object gets autowired. That class will deal with the actual ORM
	 * framework, in this case, Hibernate.
	 */
	@Autowired
	private UserDao userDao;
<<<<<<< HEAD
=======
	public UserServiceImpl() {
	}

	public UserServiceImpl(final UserDao userDao) {
		this.userDao = userDao;
	}
>>>>>>> develop/Grot.190119.1252

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public void saveAddress(Address address) {
		userDao.saveAddress(address);
	}

	@Override
<<<<<<< HEAD
	public User readUser(int userId) {
=======
	public User readUser(final int userId) {
>>>>>>> develop/Grot.190119.1252
		return userDao.readUser(userId);
	}

	@Override
<<<<<<< HEAD
	public User readUser(String userName) {
=======
	public User readUser(final String userName) {
>>>>>>> develop/Grot.190119.1252
		return userDao.readUser(userName);
	}

	@Override
<<<<<<< HEAD
	public Address readAddress(int userId) {
=======
	public Address readAddress(final int userId) {
>>>>>>> develop/Grot.190119.1252
		return userDao.readAddress(userId);
	}

	@Override
<<<<<<< HEAD
	public Role readRole(String roleName) {
=======
	public Role readRole(final String roleName) {
>>>>>>> develop/Grot.190119.1252
		return userDao.readRole(roleName);
	}

	@Override
<<<<<<< HEAD
=======
	public Role readRole(int userId) {
		return userDao.readRole(userId);
	}

	@Override
>>>>>>> develop/Grot.190119.1252
	public List<Role> readRoles() {
		return userDao.readRoles();
	}

	@Override
<<<<<<< HEAD
	public User loginUser(String userName, String password) {
=======
	public Set<Role> readRoles(int userId) {
		return userDao.readRoles(userId);
	}

	@Override
	public List<String> readRoleNames() {
		return userDao.readRoleNames();
	}

	@Override
	public List<String> readRoleNames(int userId) {
		return userDao.readRoleNames(userId);
	}

	@Override
	public User loginUser(final String userName, final String password) {
>>>>>>> develop/Grot.190119.1252
		return userDao.loginUser(userName, password);
	}

	@Override
<<<<<<< HEAD
	public void setPrincipal(HttpServletRequest req, User usr) {
=======
	public void setPrincipal(final HttpServletRequest req, final User usr) {
>>>>>>> develop/Grot.190119.1252
		AttributeProvider attr = AttributeProviderImpl.getProvider(req);
		Principal principal = Principal.getUser(usr);
		attr.setSAPrincipal(principal);
	}

	@Override
	public List<User> listProfiles() {
		return userDao.listProfiles();
	}

	/**
	 * Opvragen gelijkende veldwaarden
	 */
	@SuppressWarnings("unused")
<<<<<<< HEAD
	private List<String> listPropertiesLike(String pojoField, String pojoValue) {
		return userDao.listPropertiesLike(pojoField, pojoValue);
	}
=======
	private List<String> listPropertiesLike(final String pojoField, final String pojoValue) {
		return userDao.listPropertiesLike(pojoField, pojoValue);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.readUser(userName);
		if (user == null) {
			user = new User();
			user.setUserName(userName);
			user.setPassword("");
			user.setEnabled(false);
		}
		return getUserDetails(user);
	}

	/**
	 * Converts <u>nl.zoethout.grot.domain.User</u> to
	 * <u>org.springframework.security.core.userdetails.User</u>
	 * 
	 * @param user
	 * @param authorities
	 * @return
	 */
	private UserDetails getUserDetails(User user) {
		List<GrantedAuthority> authorities = getGrantedAuthorities(user);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<String> userRoles = readRoleNames(user.getUserId());
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		// TODO Lambda and streams
		for (String userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole));
		}
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(authorities);
		return result;
	}

>>>>>>> develop/Grot.190119.1252
}
