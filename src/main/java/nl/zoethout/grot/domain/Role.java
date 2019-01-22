package nl.zoethout.grot.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role", catalog = "db_example")
public class Role implements Serializable, Comparable<Role> {

	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<User> users = new HashSet<User>();

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	private Integer roleId;
	
	@Column(name = "role_name", nullable = false, length = 10)
	private String roleName;
	
	@Column(name = "role_desc", nullable = false, length = 255)
	private String roleDesc;

	public Role() {
		super();
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + " , roleName=" + roleName + " , roleDesc=" + roleDesc + "]";
	}
	
	@Override
	public int compareTo(Role role) {
		return this.roleName.compareTo(role.getRoleName());
	}

}