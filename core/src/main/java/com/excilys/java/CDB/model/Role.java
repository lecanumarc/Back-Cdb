package com.excilys.java.CDB.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column(name="role_name")
	String name;

	public Role() {
		super();
	}

	public Role(Long id, String Name) {
		super();
		this.id = id;
		this.name = Name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public static class Builder {
		private Long id;
		private String name;

		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Role build() {
			Role role = new Role();
			role.id = this.id;
			role.name = this.name;
			return role;
		}
	}


	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

}