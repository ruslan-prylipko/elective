package com.my.entity;

import java.io.Serializable;

public class Login implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
	private String password;
	/**
	 * Flag isEmail shows which parameter was set username or email.
	 * Value by default is false which means that was set username,
	 * true - email.
	 */
	private boolean isEmailFlag;
	
	public Login() {
		this.isEmailFlag = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets isEmailFlag which shows which parameter was set username or email.
	 * Value by default is false which means that was set username,
	 * true - email.
	 * @return - value of this flag
	 */
	public boolean isEmailFlag() {
		return isEmailFlag;
	}

	public void setEmailFlag(boolean isEmail) {
		this.isEmailFlag = isEmail;
	}

}
