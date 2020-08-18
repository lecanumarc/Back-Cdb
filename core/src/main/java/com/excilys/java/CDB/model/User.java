package com.excilys.java.CDB.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;
	private String password;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public Role getRole() {
		return role;
	}

	public static class Builder {
		private Long id;
		private String username;
		private String password;
		private Role role;

		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		public Builder setRole(Role role) {
			this.role = role;
			return this;
		}

		public User build() {
			User user = new User();
			user.id = this.id;
			user.username = this.username;
			user.password = this.password;
			user.role = this.role;
			return user;
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}

}
