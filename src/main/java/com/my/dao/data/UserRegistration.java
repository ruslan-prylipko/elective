package com.my.dao.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.RegistrationData;
import com.my.entity.User;

public class UserRegistration {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String USER_INSERT = "INSERT INTO "
			+ "user (id, username, password, first_name, middle_name, last_name, email, role_id) "
			+ "VALUES "
			+ "(DEFAULT, ?, ?, ?, ?, ?, ?, (SELECT id FROM role WHERE name=?));";
	
	private UserRegistration() {
		
	}
	
	public static boolean insert(RegistrationData regData) throws NamingException, SQLException {
		User user = regData.getUser();
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
			statement = connection.prepareStatement(USER_INSERT, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUsername());
			statement.setString(2, regData.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getMiddleName());
			statement.setString(5, user.getLastName());
			statement.setString(6, user.getEmail());
			statement.setString(7, user.getRole());
			int count = statement.executeUpdate();
			if (count > 0) {
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					user.setId(resultSet.getInt(1));
				}
			}
		} catch (NamingException e) {
			rollback(connection);
			throw e;
		} catch (SQLTimeoutException e) {
			SQLTimeoutException ex = new SQLTimeoutException("Connection to database timed out!", e);
			LOGGER.error(ex.getMessage());
			LOGGER.debug(ex);
			rollback(connection);
			throw ex;
		} catch (SQLException e) {
			SQLException ex = new SQLException("A database access error!", e);
			LOGGER.error(ex.getMessage());
			LOGGER.debug(ex);
			rollback(connection);
			throw ex;
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return true;		
	}
	
	private static void rollback(Connection connection) throws SQLException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			SQLException ex = new SQLException("Rollback exception!", e);
			LOGGER.error(ex.getMessage());
			LOGGER.debug(ex);
			throw ex;
		}
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
