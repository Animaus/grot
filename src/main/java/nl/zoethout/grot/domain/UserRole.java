package nl.zoethout.grot.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role", catalog = "db_example")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role_id", nullable = false, length = 20)
	private Integer roleId;
	@Column(name = "user_id", nullable = false, length = 7)
	private Integer userId;

	public UserRole() {
		super();
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserRole [roleId=" + roleId + ", userId=" + userId + "]";
	}
}