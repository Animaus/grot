package nl.zoethout.grot.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public void saveAddress(Address address) {
		userDao.saveAddress(address);
	}

	@Override
	public User readUser(int userId) {
		return userDao.readUser(userId);
	}

	@Override
	public User readUser(String userName) {
		return userDao.readUser(userName);
	}

	@Override
	public Address readAddress(int userId) {
		return userDao.readAddress(userId);
	}

	@Override
	public Role readRole(String roleName) {
		return userDao.readRole(roleName);
	}

	@Override
	public List<Role> readRoles() {
		return userDao.readRoles();
	}

	@Override
	public User loginUser(String userName, String password) {
		return userDao.loginUser(userName, password);
	}

	@Override
	public void setPrincipal(HttpServletRequest req, User usr) {
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
	private List<String> listPropertiesLike(String pojoField, String pojoValue) {
		return userDao.listPropertiesLike(pojoField, pojoValue);
	}
}
