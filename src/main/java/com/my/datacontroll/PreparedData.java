package com.my.datacontroll;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Prepares user's passed data to Login or Registration.
 */
/**
 * @author Ruslan
 *
 */
public class PreparedData {
	private static final Logger LOGGER = LogManager.getLogger();

	private PreparedData() {
		
	}
	
	/**
	 * Checking if a string is an username.
	 * 
	 * @param str - string to check
	 * @return true - if str is correct username.
	 * @throws IllegalArgumentException if passed username is incorrect.
	 */
	public static boolean isUsername(String str) throws IllegalArgumentException {
		baseStringCheck(str);
		return checkByPattern("^[A-Za-z][A-Za-z0-9_-]{7,29}$", str);
	}
	
	
	/**
	 * Method validates first, middle and last name.
	 * 
	 * @param str - string representing first, middle or last name.
	 * @return true - if name is valid otherwise - false.
	 */
	public static boolean isName(String str) {
		baseStringCheck(str);
		return checkByPattern("^[A-Za-z][A-Za-z\\s]{1,29}$", str);
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
		if (str == null) {
			throwInfoException("This field must be full!");
		}
		if (str.isEmpty()) {
			throwInfoException("Field mustn't be empty!");
		}
		if (str.isBlank()) {
			throwInfoException("String mustn't have white space codepoints!");
		}
	}
	
	
	/**
	 * Logging user errors by info level and throws exception.
	 * 
	 * @param message - error message
	 * @throws IllegalArgumentException which explanation user error
	 */
	private static void throwInfoException(String message) throws IllegalArgumentException {
		IllegalArgumentException e = new IllegalArgumentException(message);
		LOGGER.info(e.getMessage());
		LOGGER.debug(e);
		throw e;
	}
	
	private static boolean checkByPattern(String reg, String str) {
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
}
