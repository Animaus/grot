package nl.zoethout.grot.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nl.zoethout.grot.util.TextUtil;

@Entity
@Table(name = "user", catalog = "db_example")
public class User {

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Address address;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", catalog = "db_example", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private Set<Role> roles = new HashSet<Role>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "sex")
	private String sex;

	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "date_birth")
	private Date dateBirth;

	@Column(name = "date_registered")
	private Date dateRegistered;

	public User() {
		super();
	}

<<<<<<< HEAD
=======
	public User(User user) {
		this.address = user.getAddress();
		this.roles = user.getRoles();
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.prefix = user.getPrefix();
		this.sex = user.getSex();
		this.password = user.getPassword();
		this.enabled = user.isEnabled();
		this.dateBirth = user.getDateBirth();
		this.dateRegistered = user.getDateRegistered();
	}

>>>>>>> develop/Grot.190119.1252
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getDateBirth() {
		return this.dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getDateBirthDisplay() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(this.dateBirth);
	}

	public Date getDateRegistered() {
		return this.dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public String getDateRegisteredDisplay() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(this.dateRegistered);
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean hasRole(String roleName) {
		boolean result = false;
		for (Role role : this.roles) {
			if (role.getRoleName().equals(roleName)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public List<String> getUserRoleNames() {
		List<String> result = new ArrayList<String>();
		for (Role role : this.roles) {
			result.add(role.getRoleName());
		}
		return result;
	}

	public Set<Role> getUserRoles() {
		return this.roles;
	}

	public void changeCase() {
		setFirstName(TextUtil.toProperCase(getFirstName()));
		setLastName(TextUtil.toProperCase(getLastName()));
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + " , firstName=" + firstName + " , prefix=" + prefix + " , lastName="
				+ lastName + " , sex=" + sex + " , password=" + password + " , enabled=" + enabled + " , dateBirth="
				+ dateBirth + " , dateRegistered=" + dateRegistered + " , roles=" + roles + "]";
	}
}