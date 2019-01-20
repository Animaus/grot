package nl.zoethout.grot.domain;

import javax.persistence.Column;

//@Entity
//@Table(name = "address", catalog = "db_example")
public class AddressMine {

	// private Organization organization;
	// private Group group;

//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn(name = "user_id")
//	@JoinColumn(name = "user_id")
//	@OneToOne(fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn
	private User user;

	@Column(name = "user_id")
	private int userId;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private int addressId;

	@Column(name = "street_name")
	private String streetName;

	@Column(name = "street_number")
	private int streetNumber;

	@Column(name = "zip")
	private String zip;

	@Column(name = "city")
	private int city;

	@Column(name = "country")
	private String country;

	@Column(name = "phone1")
	private double phone1;

	@Column(name = "phone2")
	private double phone2;

	@Column(name = "email1")
	private String email1;

	@Column(name = "email2")
	private String email2;

	// -------------------------------------------------------------------
	// Velden
	// -------------------------------------------------------------------

	public AddressMine() {
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getPhone1() {
		return phone1;
	}

	public void setPhone1(double phone1) {
		this.phone1 = phone1;
	}

	public double getPhone2() {
		return phone2;
	}

	public void setPhone2(double phone2) {
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
		return "Address [user=" + user.getUserName() + " , addressId=" + addressId + " , streetName=" + streetName
				+ " , streetNumber=" + streetNumber + " , zip=" + zip + " , city=" + city + " , country=" + country
				+ " , phone1=" + phone1 + " , phone2=" + phone2 + " , email1=" + email1 + " , email2=" + email2 + "]";
	}

}