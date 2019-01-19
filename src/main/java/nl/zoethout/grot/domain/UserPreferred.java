package nl.zoethout.grot.domain;

import java.util.Collection;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public abstract class UserPreferred {
	private Integer id;
	private String password;
	private boolean loginStatus;
	private boolean isAdmin;
	private Date registerDate;
	private Date lastEdited;
	private String firstName;
	private String lastName;
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",
	// cascade=CascadeType.ALL)
	private Collection<Tent> tents;
	private Address address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(Date lastEdited) {
		this.lastEdited = lastEdited;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Collection<Tent> getTents() {
		return tents;
	}

	public void setTents(Collection<Tent> tents) {
		this.tents = tents;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public boolean verifyLogin() {
		return false;
	}
}