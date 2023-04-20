package com.my.dao.course;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
	
	@AfterAll
	static void globalClean() throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
