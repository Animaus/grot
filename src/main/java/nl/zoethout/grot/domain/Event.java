package nl.zoethout.grot.domain;

import java.util.Vector;
import java.util.Date;

public class Event {
	private String name;
	public String location;
	public String description;
	public String compensation;
	public Date dateStart;
	public String notifications;
	public Date dateEnd;
	public Date dateCancel;
	public Date dateDeadline;
	public String website;
	public Organization organization;
	public String releaseInfo;
	
	public Vector<UserPreferred> groupMember;
	public Vector<Administrator> aanmaken;
	
	public Event() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompensation() {
		return compensation;
	}

	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public String getNotifications() {
		return notifications;
	}

	public void setNotifications(String notifications) {
		this.notifications = notifications;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateCancel() {
		return dateCancel;
	}

	public void setDateCancel(Date dateCancel) {
		this.dateCancel = dateCancel;
	}

	public Date getDateDeadline() {
		return dateDeadline;
	}

	public void setDateDeadline(Date dateDeadline) {
		this.dateDeadline = dateDeadline;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getReleaseInfo() {
		return releaseInfo;
	}

	public void setReleaseInfo(String releaseInfo) {
		this.releaseInfo = releaseInfo;
	}

}