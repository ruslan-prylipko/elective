package com.my.datacontroll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Prepares user's passed data to Login or Registration.
 */
public class PreparedData {
	private static final Logger LOGGER = LogManager.getLogger();

	private PreparedData() {
		
	}
	
	/**
	 * Checking if a string is an username.
	 * @param str - string to check
	 * @return - return true if str is correct username.
	 * @throws IllegalArgumentException
	 */
	public static boolean isUsername(String str) throws IllegalArgumentException {
		baseStringCheck(str);
		return checkByPattern("^[A-Za-z][A-Za-z0-9_-]{7,29}$", str);
	}
	
	/**
	 * Checking if a string is an email.
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
		IllegalArgumentException exception = null;
		if (str == null) {
			exception = new IllegalArgumentException("This field must be full!");
			LOGGER.error(exception.getMessage());	
			LOGGER.debug(exception);
			throw exception;
		}
		if (str.isEmpty()) {
			exception = new IllegalArgumentException("Field mustn't be empty!");
			LOGGER.error(exception.getMessage());	
			LOGGER.debug(exception);
			throw exception;
		}
		if (str.isBlank()) {
			exception = new IllegalArgumentException("String mustn't have white space codepoints!");
			LOGGER.error(exception.getMessage());	
			LOGGER.debug(exception);
			throw exception;
		}
	}
	
	private static boolean checkByPattern(String reg, String str) {
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
}
