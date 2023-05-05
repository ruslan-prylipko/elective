package com.my.dao.course;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.my.dao.data.UtilDB;

public class StatusDAOTest {
	private static Connection connection = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillDataBase();
		connection = UtilDB.getConnection();
	}
	
	@Test
	void testGetStatuses() throws NamingException, SQLException {
		assertArrayEquals(StatusDAO.getStatuses().toArray(), StatusDAO.getStatuses().toArray());
	}
	
	@AfterAll
	static void globalClean() throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
