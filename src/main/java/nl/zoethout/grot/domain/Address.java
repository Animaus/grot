package nl.zoethout.grot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import nl.zoethout.grot.util.CountryCode;
import nl.zoethout.grot.util.TextUtil;

@Entity
@Table(name = "address", catalog = "db_example")
public class Address {

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private User user;

	@Id
	@Column(name = "user_id")
	private int userId;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "street_name")
	private String streetName;
	@Column(name = "street_number")
	private String streetNumber;
	@Column(name = "zip")
	private String zip;
	@Column(name = "city")
	private String city;
	@Column(name = "country")
	private String country;
	@Column(name = "phone1")
	private String phone1;
	@Column(name = "phone2")
	private String phone2;
	@Column(name = "email1")
	private String email1;
	@Column(name = "email2")
	private String email2;

	public Address() {
		super();
	}

	// -------------------------------------------------------------------
	// Getters en setters voor koppelingen
	// -------------------------------------------------------------------
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// -------------------------------------------------------------------
	// Getters en setters voor velden
	// -------------------------------------------------------------------

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryName() {
		return CountryCode.getName(country);
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	@Override
	public String toString() {
		return "Address [userName=" + userName + ", streetName=" + streetName + ", streetNumber=" + streetNumber
				+ ", zip=" + zip + ", city=" + city + ", country=" + country + "]";
	}

	// TODO 26 - Users - fieldvalidation - mutable user
	public String getClassName() {
		return this.getClass().getCanonicalName();
	}

	// TODO 25 - Users - save member with values
	public void changeCase() {
		// UpperCase
		setZip(getZip().toUpperCase());
		// LowerCase
		setEmail1(getEmail1().toLowerCase());
		setEmail2(getEmail2().toLowerCase());
		// ProperCase
		setCity(TextUtil.toProperCase(getCity()));
		setStreetName(TextUtil.toProperCase(getStreetName()));
	}

}