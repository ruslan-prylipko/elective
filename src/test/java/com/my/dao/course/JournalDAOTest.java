package com.my.dao.course;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.my.dao.data.UtilDB;

public class JournalDAOTest {
private static Connection connection = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillDataBase();
		connection = UtilDB.getConnection();
	}
	
	@Test
	void testRegistration() throws SQLException {
		assertTrue(JournalDAO.registration(1, 2));
	}
	
	@Test
	void testDoubleRegistration() throws SQLException {
		assertTrue(JournalDAO.registration(7, 3));
		assertThrows(SQLException.class, () -> JournalDAO.registration(7, 3));
	}
	
	@Test
	void testSetStudentMark() throws SQLException, NamingException {
		assertTrue(JournalDAO.setStudentMark(1, 1, 5));
		assertEquals(5, CourseDAO.getUserCourses(1).stream().filter(t -> t.getId() == 1).findAny().get().getMark());
	}
	
	@Test
	void testNotNullGetTeacherCourses() throws NamingException, SQLException {
		assertNotNull(JournalDAO.getTeacherCourses(2));
	}
	
	@Test
	void testGetTeacherCourses() throws NamingException, SQLException {
		assertArrayEquals(JournalDAO.getTeacherCourses(2).toArray(), JournalDAO.getTeacherCourses(2).toArray());
	}
	
	@AfterAll
	static void globalClean() throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
