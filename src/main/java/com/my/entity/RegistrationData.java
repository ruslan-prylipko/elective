package com.my.entity;

import java.util.Objects;

public class RegistrationData {
	private User user;
	private String password;
	
	public RegistrationData(User user, String password) {
		this.user = user;
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationData other = (RegistrationData) obj;
		return Objects.equals(password, other.password) && Objects.equals(user, other.user);
	}
}
