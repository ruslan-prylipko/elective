package com.my.dao.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.User;

public class StudentDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String SELECT_ALL_USERS = 
			"SELECT user.id, user.username, user.first_name, user.middle_name, user.last_name, user.email, user_status.name status "
			+ "FROM user "
			+ "LEFT JOIN user_status ON user.user_status_id=user_status.id "
			+ "LEFT JOIN role ON user.role_id = role.id "
			+ "WHERE role.id = (SELECT id FROM role WHERE name = \"student\");";
	
	private static final String CHANGE_STATUS_BY_USER_ID = 
			"UPDATE user SET user_status_id = (SELECT id FROM user_status WHERE name = ?) WHERE id = ?;";
	
	private StudentDAO() {
	}
	
	/**
	 * Returns all students.
	 * 
	 * @return {@code List<User>}
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static List<User> getStudents() throws SQLException, NamingException {
		List<User> users = null;
		User user = null;
		DataSource ds = null;
		Statement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(SELECT_ALL_USERS);
			users = new ArrayList<>();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("first_name"));
				user.setMiddleName(rs.getString("middle_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setStatus(rs.getString("status"));
				users.add(user);
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
		} catch (NamingException e) {
			throw e;
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return users;
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

	/**
	 * Change status for user.
	 * 
	 * @param userId
	 * @param toStatus
	 * @return {@code true} if change status was successful, otherwise {@code false}
	 * @throws SQLException
	 */
	public static boolean changeStatus(long userId, String toStatus) throws SQLException {
		boolean statusFlag = false;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
			statement = connection.prepareStatement(CHANGE_STATUS_BY_USER_ID);
			statement.setString(1, toStatus);
			statement.setLong(2, userId);
			statement.executeUpdate();
			statusFlag = true;
		} catch (SQLTimeoutException e) {
			SQLTimeoutException ex = new SQLTimeoutException("Connection to database timed out!", e);
			LOGGER.error(ex.getMessage());
			LOGGER.debug(ex);
			rollback(connection);
		} catch (NamingException | SQLException e) {
			rollback(connection);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}		
		return statusFlag;
	}
}
