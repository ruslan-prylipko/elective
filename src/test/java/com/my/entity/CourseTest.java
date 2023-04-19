package com.my.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

class CourseTest {
	
	@Test
	void testGetFormatDate() {
		Calendar calendar = Calendar.getInstance();
		/*
		 * month set by expression '05 - 1'
		 * because month at this method start from 0, so May here is 04.
		 */
		calendar.set(2023, 05 - 1, 15);	
		
		assertEquals("2023-05-15", Course.getFormatDate("yyyy-MM-dd", calendar.getTime()));
		assertEquals("2023:05:15", Course.getFormatDate("yyyy:MM:dd", calendar.getTime()));
		assertEquals("23/05/15", Course.getFormatDate("yy/MM/dd", calendar.getTime()));
	}
	
	@Test
	void testNullPatternGetFormatDate() {
		Calendar calendar = Calendar.getInstance();
		/*
		 * month set by expression '05 - 1'
		 * because month at this method start from 0, so May here is 04.
		 */
		calendar.set(2023, 05 - 1, 15);	
		
		assertThrows(NullPointerException.class, () -> Course.getFormatDate(null, calendar.getTime()));
	}
	
	@Test
	void testBlankPatternGetFormatDate() {
		Calendar calendar = Calendar.getInstance();
		/*
		 * month set by expression '05 - 1'
		 * because month at this method start from 0, so May here is 04.
		 */
		calendar.set(2023, 05 - 1, 15);	
		
		assertThrows(IllegalArgumentException.class, () -> Course.getFormatDate("", calendar.getTime()));
	}

}
