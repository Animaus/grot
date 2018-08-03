package nl.zoethout.grot.domain;

import java.util.Vector;

public class Organization {
	public String website;
	
	public Vector<Address> address;
	public Vector<Administrator> onderhouden;
	
	public Organization() {
		super();
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}