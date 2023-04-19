package com.my.dao.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.my.entity.Login;
import com.my.entity.User;

/**
 * For run this test class it's need to uncommented getDataSource() method in DataSourceConfig.java file.
 */
class UserLoginTest {

private static Connection connection = null;
	
	@BeforeAll
	static void globalSetUp() throws SQLException, IOException {
		UtilDB.createDB();
		UtilDB.fillRoleTable();
		UtilDB.insertUser();
		connection = UtilDB.getConnection();
	}
	
	@Test
	void testCorrectLogin() throws SQLException, IOException, NamingException, LoginException {
		final String SELECT_USER_BY_USENAME = 
				"SELECT user.id, user.username, user.first_name, user.middle_name, user.last_name, user.email, role.name "
				+ "FROM user "
				+ "LEFT JOIN role ON user.role_id=role.id "
				+ "WHERE user.username = ? AND user.password = ?;";
		
		Login loginEmail = new Login();
		Login loginUsername = new Login();
		
		loginEmail.setEmailFlag(true);
		loginEmail.setEmail("emily.davis@example.com");
		loginEmail.setPassword("3dR8#nC2!");
		
		loginUsername.setUsername("edavis-edavis");
		loginUsername.setPassword("3dR8#nC2!");
		
		assertNotNull(UserLogin.select(loginEmail));
		assertNotNull(UserLogin.select(loginUsername));
		
		PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_USENAME);
		statement.setString(1, "edavis-edavis");
		statement.setString(2, "3dR8#nC2!");
		ResultSet rs = statement.executeQuery();
		
		User user = new User();	
		while (rs.next()) {
			user.setId(rs.getLong("id"));
			user.setUsername(rs.getString("username"));
			user.setFirstName(rs.getString("first_name"));
			user.setMiddleName(rs.getString("middle_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setRole(rs.getString("name"));
		}
		
		assertEquals(user, UserLogin.select(loginEmail));
		assertEquals(user, UserLogin.select(loginUsername));
	}
	
	@Test
	void testInCorrectLogin() {
		Login loginEmail = new Login();
		
		loginEmail.setEmailFlag(true);
		loginEmail.setEmail("emly.davis@example.com"); // Error is here, must be 'emily.davis@example.com'
		loginEmail.setPassword("3dR8#nC2!");
		
		assertThrows(LoginException.class, () -> UserLogin.select(loginEmail));
	}
	
	@AfterAll
	static void globalClean( ) throws SQLException, IOException {
		connection.close();
		UtilDB.dropDB();
	}
}
