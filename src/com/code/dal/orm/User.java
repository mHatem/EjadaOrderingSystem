package com.code.dal.orm;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
@NamedQueries({
	@NamedQuery(
		name = "User.byUsernameAndPassword",
		query = "SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username) AND (u.password = :password OR :password IS NULL)"
	),
	@NamedQuery(
		name = "User.all",
		query = "SELECT u FROM User u"
	),
	@NamedQuery(
		name = "User.byId",
		query = "SELECT u FROM User u WHERE u.id = :id"
	)
})
public class User implements Serializable {
	private Long id;
	private String username;
	private String password;
	private String name;
	private String phoneNo;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name = "sequence_generator", sequenceName = "USER_SEQUENCE", allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username != null)
			username = username.toLowerCase();
		this.username = username;
	}

	@Column(name = "PASS")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
