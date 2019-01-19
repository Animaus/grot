package nl.zoethout.grot.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import nl.nofas.util.TextUtil;
import nl.zoethout.grot.service.UserService;
import nl.zoethout.grot.util.CountryCode;
import nl.zoethout.grot.util.TextUtil;

public class Member extends UserWrapper {

	private UserService userService;

	private Address address;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Member() {
		super();
	}

	public Member(User user) {
		this.user = user;
		this.address = user.getAddress();
	}

	public Member(UserService userService, User user, Address address) {
		this.userService = userService;
		this.user = user;
		this.address = address;
	}

	public void revive(UserService service, int userId) {
		this.userService = service;
		setUser(userService.readUser(userId));
		setAddress(userService.readAddress(userId));
	}

	// TODO 25 - Users - save member with values - Change case
	public void changeCase() {
		// UpperCase
		setZip(getZip().toUpperCase());
		// LowerCase
		setEmail1(getEmail1().toLowerCase());
		setEmail2(getEmail2().toLowerCase());
		// ProperCase
		setFirstName(TextUtil.toProperCase(getFirstName()));
		setLastName(TextUtil.toProperCase(getLastName()));
		setCity(TextUtil.toProperCase(getCity()));
		setStreetName(TextUtil.toProperCase(getStreetName()));
	}
	
	public boolean save() {
		boolean result = false;
		if (saveUser()) {
			if (saveAddress()) {
				user.setAddress(address);
				userService.saveUser(user);
				result = true;
			}
		}
		return result;
	}

	private boolean saveUser() {
		try {
			userService.saveUser(user);
			user = userService.readUser(user.getUserName());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean saveAddress() {
		try {
			int id = user.getUserId();
			if (address.getUser() == null) {
				String name = user.getUserName();
				address.setUser(user);
				address.setUserId(id);
				address.setUserName(name);
			}
			userService.saveAddress(address);
			address = userService.readAddress(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<String> getUserRoleNames() {
		List<String> result = new ArrayList<String>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			result.add(role.getRoleDesc());
		}
		return result;
	}

	public String getDateBirthDisplay() {
		return sdf.format(user.getDateBirth());
	}

	public String getDateRegisteredDisplay() {
		return sdf.format(user.getDateRegistered());
	}

	public String getStreetName() {
		return address.getStreetName();
	}

	public void setStreetName(String streetName) {
		address.setStreetName(streetName);
	}

	public String getStreetNumber() {
		return address.getStreetNumber();
	}

	public void setStreetNumber(String streetNumber) {
		address.setStreetNumber(streetNumber);
	}

	public String getZip() {
		return address.getZip();
	}

	public void setZip(String zip) {
		address.setZip(zip);
	}

	public String getCity() {
		return address.getCity();
	}

	public void setCity(String city) {
		address.setCity(city);
	}

	public String getCountry() {
		return address.getCountry();
	}

	public String getCountryName() {
		return CountryCode.getName(address.getCountry());
	}

	public void setCountry(String country) {
		address.setCountry(country);
	}

	public String getPhone1() {
		return address.getPhone1();
	}

	public void setPhone1(String phone) {
		address.setPhone1(phone);
	}

	public String getPhone2() {
		return address.getPhone2();
	}

	public void setPhone2(String phone) {
		address.setPhone2(phone);
	}

	public String getEmail1() {
		return address.getEmail1();
	}

	public void setEmail1(String email) {
		address.setEmail1(email);
	}

	public String getEmail2() {
		return address.getEmail2();
	}

	public void setEmail2(String email) {
		address.setEmail2(email);
	}
}