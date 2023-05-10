package com.my.dao.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.Login;
import com.my.entity.User;

public class UserLogin {
	private static final Logger LOGGER = LogManager.getLogger();

	private static final String SELECT_USER_BY_USENAME = 
			"SELECT user.id, user.username, user.first_name, user.middle_name, user.last_name, user.email, role.name, user_status.name status "
			+ "FROM user "
			+ "LEFT JOIN role ON user.role_id=role.id "
			+ "LEFT JOIN user_status ON user.user_status_id = user_status.id "
			+ "WHERE user.username = ? AND user.password = ?;";
	private static final String SELECT_USER_BY_EMAIL = 
			"SELECT user.id, user.username, user.first_name, user.middle_name, user.last_name, user.email, role.name, user_status.name status "
			+ "FROM user "
			+ "LEFT JOIN role ON user.role_id=role.id "
			+ "LEFT JOIN user_status ON user.user_status_id = user_status.id "
			+ "WHERE user.email = ? AND user.password = ?;";

	private UserLogin () {
		
	}
	
	/**
	 * Select user from database by passed login parameters.
	 * 
	 * @param login - Login type
	 * @return User object
	 * @throws SQLException
	 * @throws NamingException
	 * @throws LoginException
	 */
	public static User select(Login login) throws SQLException, NamingException, LoginException {
		User user = null;
		DataSource ds = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
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
				user.setRole(rs.getString("name"));
				user.setStatus(rs.getString("status"));
			}
			if (user.isEmpty()) {
				throw new LoginException("Wrong data to log in!");
			}
			if (user.getStatus().equalsIgnoreCase("locked")) {
				throw new LoginException("Your account was locked.\n"
						+ "Please, contact to support service for solution this issue by email support@example.com.\n");
			}
		} catch (SQLTimeoutException e) {
			SQLTimeoutException ex = new SQLTimeoutException("Connection to database timed out!", e);
			LOGGER.error(ex.getMessage());
			LOGGER.debug(ex);
			throw ex;
		} catch (SQLException e) {
			SQLException ex = new SQLException("A database access error!", e);
			LOGGER.error(ex.getMessage());
			LOGGER.debug(ex);
			throw ex;
		} catch (LoginException e) {
			LOGGER.error(e.getMessage());
			LOGGER.debug(e);
			throw e;
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return user;
	}

	private static void close(AutoCloseable resourse) {
		if (resourse != null) {
			try {
				resourse.close();
			} catch (Exception e) {
				Exception ex = new Exception("The resource cannot be closed!", e);
				LOGGER.error(ex.getMessage());
				LOGGER.debug(ex);
			}
		}
	}
}
