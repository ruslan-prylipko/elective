package com.my.dao.data;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StudentDAOTest {
	private static Connection connection = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillDataBase();
		connection = UtilDB.getConnection();
	}
	
	@Test
	void testGetStudents() throws SQLException, NamingException {
		assertArrayEquals(StudentDAO.getStudents().toArray(), StudentDAO.getStudents().toArray());
	}
	
	@Test
	void testChangeStatus() throws SQLException {
		assertTrue(StudentDAO.changeStatus(1, "locked"));
		assertTrue(StudentDAO.changeStatus(1, "unlocked"));
	}
	
	@AfterAll
	static void globalClean() throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
