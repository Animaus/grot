package nl.zoethout.grot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import nl.nofas.dao.UserDao;
//import nl.nofas.model.Category;
//import nl.nofas.model.Country;
//import nl.nofas.model.Group;
//import nl.nofas.model.User;
//import nl.nofas.model.UserProfile;
//import nl.nofas.util.DateTimeUtil;
//import nl.nofas.util.DevUtil;
//import nl.nofas.util.TextUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.zoethout.grot.dao.UserDao;
import nl.zoethout.grot.domain.User;

/*
 * This is the meta data that Spring will use when autowiring the eventService
 * property in the Controller created in the previous step. We?ve explicitly
 * defined the name ?userService? in the annotation?s value, otherwise without
 * it Spring would have named the bean equal to the classname automatically and the
 * autowiring of this bean in the Controller and other classes would have
 * failed.
 */
//@Service("userService")
/*
 * Annotation will be recognized by the tx:annotation-driven element in the
 * application context. Having placed the annotation at the class level means
 * that all methods in this class by default should adhere to these transaction
 * rules. "propagation = Propagation.SUPPORTS" means a transaction isn?t
 * necessary, but if one exists, then the method will run in that transaction.
 * The readOnly=true attribute is pretty straight forward, by default all data
 * retrieved should be read only. No writes to the datasource are permitted.
 */
//@Transactional
// (propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceNofas {

	/*
	 * DAO object gets autowired. That class will deal with the actual ORM
	 * framework, in this case, Hibernate.
	 */
	// @Autowired
	private UserDao userDao;
	private String strClass = getClass().getName();

	// @Override
	// public List<UserProfile> listProfiles() {
	// return userDao.listProfiles();
	// }

	// @Override
	// public void saveUser(User user) {
	// userDao.saveUser(user);
	// }

	// @Override
	// public void saveCategory(Category category){
	// userDao.saveCategory(category);
	// }

	// @Override
	// public User readUser(String userName) {
	// return userDao.readUser(userName);
	// }

	// @Override
	// public List<Group> readGroups() {
	// return userDao.readGroups();
	// }

	// @Override
	// public List<Country> readCountries() {
	// return userDao.readCountries();
	// }

	// @Override
	// public Group readGroup(String groupName) {
	// return userDao.readGroup(groupName);
	// }

	// /**
	// * Opvragen identieke veldwaarden
	// */
	// private List<String> listPropertiesSame(String pojoField, String pojoValue) {
	// return userDao.listPropertiesSame(pojoField, pojoValue);
	// }

	// /**
	// * Opvragen gelijkende veldwaarden
	// */
	// private List<String> listPropertiesLike(String pojoField, String pojoValue) {
	// return userDao.listPropertiesLike(pojoField, pojoValue);
	// }

	// -------------------------------------------------------------------
	// Validaties
	// -------------------------------------------------------------------

	private String validField(String fieldName, String fieldValue, int maximum, int userID) {
		String strError = "";
		int intUser = 0;

		// Voorkomen check op zelf
		if (userID > 0) {
			intUser = 1;
		}

		if (fieldValue.length() > 0 & fieldValue.length() < maximum) {
			strError = "field.invalid";
		} else if (fieldValue.length() > 0) {
			List<String> result = listPropertiesSame(fieldName, fieldValue);
			int checkResult = result.size() - intUser;
			if (checkResult > 0) {
				strError = "field.used";
			}
		}

		return strError;
	}

	// @Override
	public String validPhoneNumber(User user) {
		// String fieldValue = user.getPhoneNumber();
		String fieldValue = "";
		int userID = user.getUserId();
		String errorMessage = validField("phoneNumber", fieldValue, 10, userID);
		return errorMessage;
	}

	// @Override
	public String validMobileNumber(User user) {
		// String fieldValue = user.getMobileNumber();
		String fieldValue = "";
		int userID = user.getUserId();
		String errorMessage = validField("mobileNumber", fieldValue, 10, userID);
		return errorMessage;
	}

	// @Override
	public String validMailAddress(User user) {
		// String fieldValue = user.getMailAddress();
		String fieldValue = "";
		int userID = user.getUserId();
		String errorMessage = validField("mailAddress", fieldValue, -1, userID);
		return errorMessage;
	}

	/**
	 * Opvragen identieke veldwaarden
	 */
	private List<String> listPropertiesSame(String pojoField, String pojoValue) {
		return userDao.listPropertiesSame(pojoField, pojoValue);
	}

}