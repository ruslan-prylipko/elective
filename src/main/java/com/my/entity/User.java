package com.my.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String role;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isEmpty() {
		if (id == 0 && username == null && firstName == null && middleName == null && lastName == null && email == null && role == null) {
			return true;
		}
		return false;
	}	
	
	@Override
	public int hashCode() {
		return Objects.hash(email, id, username);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(username, other.username);
	}
	
	@Override
	public String toString() {
		return "id = " + id + ", username = " + username + ", firstName = " + firstName + ", middleName = " + middleName
				+ ", lastName = " + lastName + ", email = " + email + ", role = " + role;
	}
}
