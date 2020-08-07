package com.excilys.java.CDB.DTO;

public class UserDTO {
	
	private String userId;
	private String username;
	private String password;
	private String role;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append(" id :").append(this.userId);
		sb.append(", username :").append(this.username);
		sb.append(", password :").append(this.password);
		sb.append(", role :").append(this.role);
		return sb.toString();
	}
	
}
