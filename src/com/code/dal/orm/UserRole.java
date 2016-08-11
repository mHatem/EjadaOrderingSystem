package com.code.dal.orm;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_ROLES")
@NamedQuery(
	name = "UserRole.byUserId",
	query = "SELECT r FROM UserRole r where r.userId = :userId"
)
public class UserRole implements Serializable {
	private Long id;
	private String roleName;
	private Long userId;

	public static final String NORMAL = "NORMAL";
	public static final String ADMIN = "ADMIN";
	public static final String ADMIN_PLACES = "ADMIN_PLACES";
	public static final String ADMIN_ITEMS = "ADMIN_ITEMS";

	@Id
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "USER_ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
