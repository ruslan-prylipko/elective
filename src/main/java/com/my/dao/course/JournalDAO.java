package com.my.dao.course;

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

public class JournalDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String JOURLAN_COURSE_INSERT = 
			"INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, status_id) "
			+ "SELECT course.id, user.id, CURRENT_DATE(), course.start_date, course.end_date, course.status_id "
			+ "FROM course "
			+ "JOIN user ON course.id = ? AND user.id = ?;";
	
	
	private JournalDAO() {
	}

	/**
	 * Registers student on the course.
	 * 
	 * @param userId
	 * @param courseId
	 * @return {@code true} if registration was successful, otherwise {@code false}
	 * @throws SQLException
	 */
	public static boolean registration(long userId, long courseId) throws SQLException {
		boolean regFlag = false;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
			statement = connection.prepareStatement(JOURLAN_COURSE_INSERT);
			statement.setLong(1, courseId);
			statement.setLong(2, userId);
			statement.executeUpdate();
			regFlag = true;
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
		return regFlag;
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
