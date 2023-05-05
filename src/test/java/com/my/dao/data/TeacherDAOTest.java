package com.my.dao.data;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TeacherDAOTest {
	private static Connection connection = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillDataBase();
		connection = UtilDB.getConnection();
	}
	
	@Test
	void testGetTeachers() throws NamingException, SQLException {
		assertArrayEquals(TeacherDAO.getTeachers().toArray(), TeacherDAO.getTeachers().toArray());
	}
	
	@AfterAll
	static void globalClean() throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
