package com.my.dao.course;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.my.dao.data.UtilDB;
import com.my.entity.ShortCourse;

/**
 * For run this test class it's need to uncommented getDataSource() method in DataSourceConfig.java file.
 */
class CourseDAOTest {
	private static Connection connection = null;
	private static long courseId;
	private static Calendar starDate = null;
	private static Calendar endDate = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillDataBase();
		connection = UtilDB.getConnection();
		
		starDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		starDate.set(2023, 04, 8); // 04 is May
		endDate.set(2023, 06, 19);
	}
	
	@Test
	void testInsertNewCourse() throws SQLException {
		assertNotEquals(-1, 
				CourseDAO.insertNewCourse(
						"Exploring Web Development", "6 week", 
						new Date(starDate.getTimeInMillis()), 
						new Date(endDate.getTimeInMillis()), 1, 2, 1));
		
		assertThrows(SQLException.class, 
				() -> CourseDAO.insertNewCourse(
						"Exploring Web Development", "6 week", 
						new Date(starDate.getTimeInMillis()), 
						new Date(endDate.getTimeInMillis()), 1, 2, 1));
	}
	
	@Test
	void testUpdateCourse() throws SQLException {
		courseId = CourseDAO.insertNewCourse(
				"Web Development Essentials", 
				"6 week", new Date(starDate.getTimeInMillis()), 
				new Date(endDate.getTimeInMillis()), 1, 2, 1);
		
		assertTrue(
				CourseDAO.updateCourse(
						courseId, "Getting Started with Web Development", 
						"6 week", new Date(starDate.getTimeInMillis()), 
						new Date(endDate.getTimeInMillis()), 1, 2, 1));
	}
	
	@Test
	void testGetCourseById() throws NamingException, SQLException {
		ShortCourse shortCourse = new ShortCourse();
		shortCourse.setId(courseId);
		shortCourse.setName("Getting Started with Web Development");
		shortCourse.setDuration("6 week");
		shortCourse.setStartDate(starDate);
		shortCourse.setEndDate(endDate);
		shortCourse.setTopicId(1);
		shortCourse.setTeacherId(2);
		shortCourse.setStatusId(1);
		assertEquals(shortCourse, CourseDAO.getCourseById(courseId));
	}
	
	@Test
	void testGetUserCourses() throws NamingException, SQLException {
		assertArrayEquals(CourseDAO.getUserCourses(7).toArray(), CourseDAO.getUserCourses(7).toArray());
	}
		
	@Test
	void testNotNullGetUserCourses() throws NamingException, SQLException {
		assertNotNull(CourseDAO.getUserCourses(7));	// userId = 7
	}	
	
	@Test
	void testGetAvailableCourses() throws NamingException, SQLException {
		assertArrayEquals(CourseDAO.getAvailableCourses(7).toArray(), CourseDAO.getAvailableCourses(7).toArray());
	}
	
	@Test
	void testNotNullGetAvailableCourses() throws NamingException, SQLException {
		assertNotNull(CourseDAO.getAvailableCourses(7));	// userId = 7
	}
	
	@Test
	void testDeleteCourse() throws SQLException, NamingException {
		assertFalse(CourseDAO.getAllCourses().stream().filter(p -> p.getId() == 10).findAny().isEmpty());
		assertTrue(CourseDAO.deleteCourse(10));
		assertTrue(CourseDAO.getAllCourses().stream().filter(p -> p.getId() == 10).findAny().isEmpty());
	}
	
	@Test
	void testGetAllCourses() throws NamingException, SQLException {
		assertArrayEquals(CourseDAO.getAllCourses().toArray(), CourseDAO.getAllCourses().toArray());
	}
	
	@AfterAll
	static void globalClean() throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
