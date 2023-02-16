package com.my.datacontroll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PreparedDataTest {
	
	// Tests for isEmail() method
	
	@Test
	void testIsEmailNullInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isEmail(null));
	}
	
	@Test
	void testIsEmailEmptyInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isEmail(""));
	}
	
	@Test
	void testIsEmailBlankInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isEmail(" 	"));
	}
	
	@Test
	void testIsEmailCorrectInput() {
		assertTrue(() -> PreparedData.isEmail("user.test@gmail.com"));
		assertTrue(() -> PreparedData.isEmail("user2563@gmail.com"));
		assertTrue(() -> PreparedData.isEmail("user@gmail.com"));
		assertTrue(() -> PreparedData.isEmail("user_bambino@i.ua"));
		assertTrue(() -> PreparedData.isEmail("user-macho@ukr.net"));
		assertTrue(() -> PreparedData.isEmail("82231user@gmail.com"));
	}
	
	@Test
	void testIsEmailIncorrectInput() {
		assertFalse(() -> PreparedData.isEmail("user%test@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user/test@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user+user@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user\\\\-_@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("aaaaaaaa\\\\\\\\\\\\\\\\\\\\\\\\@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("/////////@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user/test@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user@test@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user|test@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user&test@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("user*test@gmail.com"));
		assertFalse(() -> PreparedData.isEmail("        @ukr.net"));
		assertFalse(() -> PreparedData.isEmail("@ukr.net"));
	}
	
	
	// Tests for isUsername() method
	
	@Test
	void testIsUsernameNullInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isUsername(null));
	}
	
	@Test
	void testIsUsernameEmptyInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isUsername(""));
	}
	
	@Test
	void testIsUsernameBlankInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isUsername(" 	"));
	}
	
	@Test
	void testIsUsernameCorrectInput() {
		assertTrue(() -> PreparedData.isUsername("users-285333"));
		assertTrue(() -> PreparedData.isUsername("users333uper"));
		assertTrue(() -> PreparedData.isUsername("usersuper12599"));
		assertTrue(() -> PreparedData.isUsername("usersuper"));
		assertTrue(() -> PreparedData.isUsername("user_bambino"));
		assertTrue(() -> PreparedData.isUsername("user-macho"));
		assertTrue(() -> PreparedData.isUsername("student_1"));
	}
	
	@Test
	void testIsUsernameIncorrectInput() {
		assertFalse(() -> PreparedData.isUsername("user%user"));
		assertFalse(() -> PreparedData.isUsername("user+user"));
		assertFalse(() -> PreparedData.isUsername("user\\-_"));
		assertFalse(() -> PreparedData.isUsername("aaaaaaaa\\\\\\\\\\\\"));
		assertFalse(() -> PreparedData.isUsername("/////////"));
		assertFalse(() -> PreparedData.isUsername("---------"));
		assertFalse(() -> PreparedData.isUsername("_________"));
		assertFalse(() -> PreparedData.isUsername("82231user"));
		assertFalse(() -> PreparedData.isUsername("user"));
		assertFalse(() -> PreparedData.isUsername("user.test"));
		assertFalse(() -> PreparedData.isUsername("user/test"));
		assertFalse(() -> PreparedData.isUsername("user|test"));
		assertFalse(() -> PreparedData.isUsername("user@test"));
		assertFalse(() -> PreparedData.isUsername("user*test"));
		assertFalse(() -> PreparedData.isUsername("user&test"));
	}
	
	// Tests for passwordValidation() method
	
	@Test
	void testPasswordValidationNullInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.passwordValidation(null));
	}
	
	@Test
	void testPasswordValidationEmptyInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.passwordValidation(""));
	}
	
	@Test
	void testPasswordValidationBlankInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.passwordValidation(" 	"));
	}
	
	@Test
	void testPasswordValidationCorrectInput() {
		assertTrue(() -> PreparedData.passwordValidation("@User456228*"));
		assertTrue(() -> PreparedData.passwordValidation("@user456228*"));
		assertTrue(() -> PreparedData.passwordValidation("@user456228"));
		assertTrue(() -> PreparedData.passwordValidation("users-285333"));
		assertTrue(() -> PreparedData.passwordValidation("@Users333uper"));
		assertTrue(() -> PreparedData.passwordValidation("*5UserSuper"));
		assertTrue(() -> PreparedData.passwordValidation("@user_bamBino3"));
		assertTrue(() -> PreparedData.passwordValidation("*user-machO1"));
	}
	
	@Test
	void testPasswordValidationIncorrectInput() {
		assertFalse(() -> PreparedData.passwordValidation("user%user"));
		assertFalse(() -> PreparedData.passwordValidation("user+user"));
		assertFalse(() -> PreparedData.passwordValidation("user\\-_"));
		assertFalse(() -> PreparedData.passwordValidation("aaaaaaaa\\\\\\\\\\\\"));
		assertFalse(() -> PreparedData.passwordValidation("/////////"));
		assertFalse(() -> PreparedData.passwordValidation("---------"));
		assertFalse(() -> PreparedData.passwordValidation("_________"));
		assertFalse(() -> PreparedData.passwordValidation("82231user"));
		assertFalse(() -> PreparedData.passwordValidation("user"));
		assertFalse(() -> PreparedData.passwordValidation("user.test"));
		assertFalse(() -> PreparedData.passwordValidation("user/test"));
		assertFalse(() -> PreparedData.passwordValidation("user|test"));
		assertFalse(() -> PreparedData.passwordValidation("user@test"));
		assertFalse(() -> PreparedData.passwordValidation("user*test"));
		assertFalse(() -> PreparedData.passwordValidation("user&test"));
		assertFalse(() -> PreparedData.passwordValidation("9874562285333"));
		assertFalse(() -> PreparedData.passwordValidation("usersuper"));
	}
	
	
}