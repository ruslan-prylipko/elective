package com.my.datacontroll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Prepares user's passed data to Login or Registration.
 */
public class PreparedData {

	private PreparedData() {
		
	}
	
	/**
	 * Сhecking if a string is an username.
	 * @param str - string to check
	 * @return - return true if str is correct username.
	 * @throws IllegalArgumentException
	 */
	public static boolean isUsername(String str) throws IllegalArgumentException {
		baseStringCheck(str);
		return checkByPattern("^[A-Za-z][A-Za-z0-9_-]{7,29}$", str);
	}
	
	/**
	 * Сhecking if a string is an email.
	 * @param str - string to check
	 * @return - return true if str is correct email.
	 * @throws IllegalArgumentException
	 */
	public static boolean isEmail(String str) throws IllegalArgumentException {
		baseStringCheck(str);
		return checkByPattern("^[A-Z0-9._-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", str);
	}
	
	/**
	 * Password is at least 8 characters long and contains at least one uppercase letter, 
	 * one lowercase letter, one number, and one special character.
	 * @param password
	 * @return - return true if str is correct password.
	 * @throws IllegalArgumentException
	 */
	public static boolean passwordValidation(String password) throws IllegalArgumentException {
		baseStringCheck(password);
		return checkByPattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^\\w\\s]).{8,}$", password);
	}

	private static void baseStringCheck(String str) throws IllegalArgumentException {
		if (str == null) {
			throw new IllegalArgumentException("This field must be full!");
		}
		if (str.isEmpty()) {
			throw new IllegalArgumentException("String mustn't be empty!");
		}
		if (str.isBlank()) {
			throw new IllegalArgumentException("String mustn't have white space codepoints!");
		}
	}
	
	private static boolean checkByPattern(String reg, String str) {
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
}
