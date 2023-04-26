package com.my.dao.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.User;
import com.my.entity.UserCourse;

public class JournalDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String JOURNAL_MARK_INSERT =
			"update journal set mark = ? where course_id = ? and student_id = ?;";
	
	private static final String JOURLAN_COURSE_INSERT = 
			"INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, status_id) "
			+ "SELECT course.id, user.id, CURRENT_DATE(), course.start_date, course.end_date, course.status_id "
			+ "FROM course "
			+ "JOIN user ON course.id = ? AND user.id = ?;";
	
	private static final String JOURNAL_TEACHER_COURSES = 
			"select journal.student_id, user.first_name, user.middle_name, user.last_name, course.id course_id, course.name course_name, course.duration, journal.registration_date, "
			+ "journal.start_date, journal.end_date, topic.name topic_name, journal.mark, status.status "
			+ "from journal "
			+ "left join course on journal.course_id = course.id "
			+ "left join topic on course.topic_id = topic.id "
			+ "left join user on journal.student_id = user.id "
			+ "left join status on course.status_id = status.id "
			+ "where course.teacher_id = ?;";
	
	
	private JournalDAO() {
	}
	
	/**
	 * Get all courses of the teacher.
	 * 
	 * @param userId
	 * @return {@code List<UserCourse>}
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static List<UserCourse> getTeacherCourses(long userId) throws NamingException, SQLException {
		List<UserCourse> coursesList = null;
		UserCourse userCourse = null;
		Calendar calendar = null;
		User student = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(JOURNAL_TEACHER_COURSES);
			statement.setLong(1,  userId);
			rs = statement.executeQuery();
			coursesList = new ArrayList<>();
			while (rs.next()) {
				userCourse = new UserCourse();
				userCourse.setStudentId(rs.getLong("student_id"));
				userCourse.setId(rs.getLong("course_id"));
				userCourse.setName(rs.getString("course_name"));
				student = new User();
				student.setFirstName(rs.getString("first_name"));
				student.setMiddleName(rs.getString("middle_name"));
				student.setLastName(rs.getString("last_name"));
				userCourse.setStudent(student);
				userCourse.setDuration(rs.getString("duration"));
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("registration_date"));
				userCourse.setRegistrationDate(calendar);
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("start_date"));
				userCourse.setStartDate(calendar);
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("end_date"));
				userCourse.setEndDate(calendar);
				userCourse.setTopic(rs.getString("topic_name"));
				userCourse.setMark(rs.getInt("mark"));
				userCourse.setStatus(rs.getString("status"));				
				coursesList.add(userCourse);
			}
		} catch (NamingException | SQLException e) {
			throw e;
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return coursesList;
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

	/**
	 * Set course mark for student.
	 * 
	 * @param courseId
	 * @param studentId
	 * @param mark
	 * @return {@code true} if mark set successful, otherwise {@code false}
	 * @throws SQLException
	 */
	public static boolean setStudentMark(long courseId, long studentId, int mark) throws SQLException {
		boolean regFlag = false;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
			statement = connection.prepareStatement(JOURNAL_MARK_INSERT);
			statement.setInt(1, mark);
			statement.setLong(2, courseId);
			statement.setLong(3, studentId);
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

}
