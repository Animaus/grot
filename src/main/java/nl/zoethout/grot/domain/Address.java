package nl.zoethout.grot.domain;

public class Address {
	private String streetName;
	private int city;
	private int streetNumber;
	private double phone1;
	private double phone2;
	private String mail1;
	private String mail2;
	private String zip;
	private String country;
	
	public User user;
	public Organization organization;
	public Group group;

	public Address() {
		super();
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
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

	public String getMail1() {
		return mail1;
	}

	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}

	public String getMail2() {
		return mail2;
	}

	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}