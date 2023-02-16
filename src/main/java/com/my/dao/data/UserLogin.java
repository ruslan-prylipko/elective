package com.my.dao.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.my.db.DataSourceConfig;
import com.my.entity.Login;
import com.my.entity.User;

public class UserLogin {

	private static final String SELECT_USER_BY_USENAME = 
			"SELECT id, username, first_name, middle_name, last_name, email "
			+ "FROM user "
			+ "WHERE username = ? AND password = ?";
	private static final String SELECT_USER_BY_EMAIL = 
			"SELECT id, username, first_name, middle_name, last_name, email "
			+ "FROM user "
			+ "WHERE email = ? AND password = ?";

	private UserLogin () {
		
	}
	
	public static User select(Login login) throws SQLException {
		User user = null;
		DataSource ds = DataSourceConfig.getDataSource();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try (Connection connection = ds.getConnection()) {
			if (login.isEmailFlag()) {
				statement = connection.prepareStatement(SELECT_USER_BY_EMAIL);
				statement.setString(1, login.getEmail());
			} else {
				statement = connection.prepareStatement(SELECT_USER_BY_USENAME);
				statement.setString(1, login.getUsername());
			}
			statement.setString(2, login.getPassword());
			rs = statement.executeQuery();
			user = new User();
			while (rs.next()) {
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("first_name"));
				user.setMiddleName(rs.getString("middle_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			System.out.println("Error!");
			throw e;
		} finally {
			close(rs);
			close(statement);
		}
		return user;
	}

	private static void close(AutoCloseable resourse) {
		if (resourse != null) {
			try {
				resourse.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
