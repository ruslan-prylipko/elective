package com.my.dao.course;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.my.dao.data.UtilDB;

/**
 * For run this test class it's need to uncommented getDataSource() method in DataSourceConfig.java file.
 */
class CourseDAOTest {
	private static Connection connection = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillDataBase();
		connection = UtilDB.getConnection();
	}
	
	@Test
	void testGetUserCourses() throws NamingException, SQLException {
		assertArrayEquals(CourseDAO.getUserCourses(7).toArray(), CourseDAO.getUserCourses(7).toArray());
	}
		
	@Test
	void testNotNullGetUserCourses() throws NamingException, SQLException {
		assertNotNull(CourseDAO.getUserCourses(7));	// userId = 7
	}	
	
	@AfterAll
	static void globalClean() throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
	

}
