package com.my.datacontroll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PreparedDataTest {
	
	@Test
	void testNullInput() {	//////////////
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isEmail(null));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isUsername(null));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.passwordValidation(null));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isName(null));
	}
	
	@Test
	void testEmptyInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isEmail(""));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isUsername(""));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.passwordValidation(""));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isName(""));
	}
	
	@Test
	void testBlankInput() {
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isEmail(" 	"));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isUsername(" 	"));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.passwordValidation(" 	"));
		assertThrows(IllegalArgumentException.class, () -> PreparedData.isName(" 	"));
	}
	
	@Test
	void testCorrectInput() {
		// Email
		assertTrue(() -> PreparedData.isEmail("user.test@gmail.com"));
		assertTrue(() -> PreparedData.isEmail("user2563@gmail.com"));
		assertTrue(() -> PreparedData.isEmail("user@gmail.com"));
		assertTrue(() -> PreparedData.isEmail("user_bambino@i.ua"));
		assertTrue(() -> PreparedData.isEmail("user-macho@ukr.net"));
		assertTrue(() -> PreparedData.isEmail("82231user@gmail.com"));
		// Username
		assertTrue(() -> PreparedData.isUsername("users-285333"));
		assertTrue(() -> PreparedData.isUsername("users333uper"));
		assertTrue(() -> PreparedData.isUsername("usersuper12599"));
		assertTrue(() -> PreparedData.isUsername("usersuper"));
		assertTrue(() -> PreparedData.isUsername("user_bambino"));
		assertTrue(() -> PreparedData.isUsername("user-macho"));
		assertTrue(() -> PreparedData.isUsername("student_1"));
		// Password
		assertTrue(() -> PreparedData.passwordValidation("@User456228*"));
		assertTrue(() -> PreparedData.passwordValidation("@user456228*"));
		assertTrue(() -> PreparedData.passwordValidation("@user456228"));
		assertTrue(() -> PreparedData.passwordValidation("users-285333"));
		assertTrue(() -> PreparedData.passwordValidation("@Users333uper"));
		assertTrue(() -> PreparedData.passwordValidation("*5UserSuper"));
		assertTrue(() -> PreparedData.passwordValidation("@user_bamBino3"));
		assertTrue(() -> PreparedData.passwordValidation("*user-machO1"));
		// Name
		assertTrue(() -> PreparedData.isName("Alice"));		// first name
		assertTrue(() -> PreparedData.isName("Smith"));		// last name
		assertTrue(() -> PreparedData.isName("Elizabeth"));	// middle name
		assertTrue(() -> PreparedData.isName("usersuper"));
	}
	
	@Test
	void testIncorrectInput() {
		// Email
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
		// Username
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
		// Password
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
		// Name
		assertFalse(() -> PreparedData.isName("user%user"));
		assertFalse(() -> PreparedData.isName("user+user"));
		assertFalse(() -> PreparedData.isName("user\\-_"));
		assertFalse(() -> PreparedData.isName("aaaaaaaa\\\\\\\\\\\\"));
		assertFalse(() -> PreparedData.isName("/////////"));
		assertFalse(() -> PreparedData.isName("---------"));
		assertFalse(() -> PreparedData.isName("_________"));
		assertFalse(() -> PreparedData.isName("82231user"));
		assertFalse(() -> PreparedData.isName("user.test"));
		assertFalse(() -> PreparedData.isName("user/test"));
		assertFalse(() -> PreparedData.isName("user|test"));
		assertFalse(() -> PreparedData.isName("user@test"));
		assertFalse(() -> PreparedData.isName("user*test"));
		assertFalse(() -> PreparedData.isName("user&test"));
		assertFalse(() -> PreparedData.isName("user_bambino"));
		assertFalse(() -> PreparedData.isName("user-macho"));
		assertFalse(() -> PreparedData.isName("student_1"));
	}
}