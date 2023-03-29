package com.my.dao.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.my.entity.RegistrationData;
import com.my.entity.User;


/**
 * For run this test class it's need to uncommented getDataSource() method in DataSourceConfig.java file.
 */
class UserRegistrationTest {
	
	private static Connection connection = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillRoleTable();
		connection = UtilDB.getConnection();
	}
	
	@Test
	void testCorrectInsert() throws NamingException, SQLException {
		final String SELECT_USER_BY_USENAME = 
				"SELECT user.id, user.username, user.first_name, user.middle_name, user.last_name, user.email, role.name "
				+ "FROM user "
				+ "LEFT JOIN role ON user.role_id=role.id "
				+ "WHERE user.username = ? AND user.password = ?;";
		
		User user = new User();
		user.setFirstName("Alice");
		user.setMiddleName("Elizabeth");
		user.setLastName("Smith");
		user.setUsername("asmith-asmith");
		user.setEmail("alice_smith@example.com");
		user.setRole("student");
		RegistrationData data = new RegistrationData(user, "5tG6#kL9@");
		
		assertTrue(UserRegistration.insert(data));
		
		PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USENAME);
		statement.setString(1, "asmith-asmith");
		statement.setString(2, "5tG6#kL9@");
		ResultSet rs = statement.executeQuery();
		
		// userTest have got from database, it must be equal to insert user
		User userTest = new User();	
		while (rs.next()) {
			userTest.setId(rs.getLong("id"));
			userTest.setUsername(rs.getString("username"));
			userTest.setFirstName(rs.getString("first_name"));
			userTest.setMiddleName(rs.getString("middle_name"));
			userTest.setLastName(rs.getString("last_name"));
			userTest.setEmail(rs.getString("email"));
			userTest.setRole(rs.getString("name"));
		}
		
		assertEquals(user, userTest);
		
		rs.close();
		statement.close();
	}
	
	@Test
	void testInCorrectInsert() throws NamingException, SQLException {
		User user = new User();
		user.setFirstName("John");
		user.setMiddleName("Michael");
		user.setLastName("Johnson");
		user.setUsername("jjohnson-jjohnson");
		user.setEmail("jjohnson@example.com");
		user.setRole("student");
		RegistrationData data = new RegistrationData(user, "7pM4$sK1%");
		
		UserRegistration.insert(data);
		
		assertThrows(SQLException.class, () -> UserRegistration.insert(data));
	}
	
	@AfterAll
	static void globalClean( ) throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
