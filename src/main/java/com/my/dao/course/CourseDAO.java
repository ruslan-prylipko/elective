package com.my.dao.course;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.Course;
import com.my.entity.ShortCourse;
import com.my.entity.User;
import com.my.entity.UserCourse;

public class CourseDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String SELECT_USER_COURSES = 
			"select course.id course_id, course.name course_name, journal.student_id, "
			+ "course.duration, journal.registration_date, "
			+ "journal.start_date, journal.end_date, "
			+ "topic.name topic_name, user.first_name, "
			+ "user.middle_name, user.last_name, journal.mark, status.status "
			+ "from journal "
			+ "left join course on journal.course_id = course.id "
			+ "left join topic on course.topic_id = topic.id "
			+ "left join user on course.teacher_id = user.id "
			+ "left join status on course.status_id = status.id "
			+ "where student_id = ?;";
	
	private static final String SELECT_AVAILABLE_COURSES = 
			"SELECT course.id, course.name course_name, course.duration, "
			+ "course.start_date, course.end_date, topic.name topic_name, user.first_name, "
			+ "user.middle_name, user.last_name, status.status "
			+ "FROM course "
			+ "LEFT JOIN topic ON course.topic_id = topic.id "
			+ "LEFT JOIN user ON course.teacher_id = user.id "
			+ "LEFT JOIN status ON course.status_id = status.id "
			+ "where course.id not in (select course_id from journal where student_id = ?);";
	
	private static final String SELECT_ALL_COURSES =
			"SELECT course.id course_id, course.name course_name, course.duration, "
			+ "course.start_date, course.end_date, topic.name topic_name, user.first_name, "
			+ "user.middle_name, user.last_name, status.status "
			+ "FROM course "
			+ "LEFT JOIN topic ON course.topic_id = topic.id "
			+ "LEFT JOIN user ON course.teacher_id = user.id "
			+ "LEFT JOIN status ON course.status_id = status.id";
	
	private static final String DELETE_COURSE_BY_ID = 
			"DELETE FROM course WHERE id = ?;";
	
	private static final String INSERT_NEW_COURSE =
			"INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id)"
			+ "VALUES (default, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_COURSE_BY_ID =
			"SELECT name course_name, duration, start_date, end_date, topic_id, teacher_id, status_id FROM course WHERE id = ?;";
	private static final String UPDATE_COURSE =
			"UPDATE course SET name = ?, duration = ?, start_date = ?, end_date = ?, topic_id = ?, "
			+ "teacher_id = ?, status_id = ? "
			+ "WHERE id = ?;";
	
	private CourseDAO() {
		
	}
	
	/**
	 * Inserts new course into DB.
	 * 
	 * @param courseName
	 * @param duration
	 * @param startDate
	 * @param endDate
	 * @param topicId
	 * @param teacherId
	 * @param statusId
	 * @return if inserts was successful - {@code courseId}, otherwise -1.
	 * @throws SQLException
	 */
	public static long insertNewCourse(String courseName, String duration, 
			Date startDate, Date endDate, long topicId, long teacherId, long statusId) throws SQLException {
		long courseId = -1;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
			statement = connection.prepareStatement(INSERT_NEW_COURSE, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, courseName);
			statement.setString(2, duration);
			statement.setDate(3, startDate);
			statement.setDate(4, endDate);
			statement.setLong(5, topicId);
			statement.setLong(6, teacherId);
			statement.setLong(7, statusId);
			int count = statement.executeUpdate();
			if (count > 0) {
				resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					courseId = resultSet.getLong(1);
				}
			}
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
		return courseId;
	}
	
	/**
	 * Returns list of courses for which the student is registered.
	 * 
	 * @param userId
	 * @return {@code List<UserCourse>}
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static List<UserCourse> getUserCourses(long userId) throws NamingException, SQLException {
		List<UserCourse> coursesList = null;
		UserCourse userCourse = null;
		Calendar calendar = null;
		User teacher = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(SELECT_USER_COURSES);
			statement.setLong(1,  userId);
			rs = statement.executeQuery();
			coursesList = new ArrayList<>();
			while (rs.next()) {
				userCourse = new UserCourse();
				userCourse.setStudentId(rs.getLong("student_id"));
				userCourse.setId(rs.getLong("course_id"));
				userCourse.setName(rs.getString("course_name"));
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
				teacher = new User();
				teacher.setFirstName(rs.getString("first_name"));
				teacher.setMiddleName(rs.getString("middle_name"));
				teacher.setLastName(rs.getString("last_name"));
				userCourse.setTeacher(teacher);
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
	 * Returns list of available courses for user.
	 * 
	 * @param userId
	 * @return {@code List<Course>}
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static List<Course> getAvailableCourses(long userId) throws NamingException, SQLException {
		List<Course> coursesList = null;
		Course course = null;
		Calendar calendar = null;
		User teacher = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(SELECT_AVAILABLE_COURSES);
			statement.setLong(1,  userId);
			rs = statement.executeQuery();
			coursesList = new ArrayList<>();
			while (rs.next()) {
				course = new Course();
				course.setId(rs.getLong("id"));
				course.setName(rs.getString("course_name"));
				course.setDuration(rs.getString("duration"));
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("start_date"));
				course.setStartDate(calendar);
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("end_date"));
				course.setEndDate(calendar);
				course.setTopic(rs.getString("topic_name"));
				teacher = new User();
				teacher.setFirstName(rs.getString("first_name"));
				teacher.setMiddleName(rs.getString("middle_name"));
				teacher.setLastName(rs.getString("last_name"));
				course.setTeacher(teacher);
				course.setStatus(rs.getString("status"));
				
				coursesList.add(course);
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
	 * Returns {@code ShortCourse} object. Object represent short information
	 * about course.
	 * 
	 * @param courseId
	 * @return {@code ShortCourse} object
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static ShortCourse getCourseById(long courseId) throws NamingException, SQLException {
		ShortCourse course = null;
		Calendar calendar = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(SELECT_COURSE_BY_ID);
			statement.setLong(1, courseId);
			rs = statement.executeQuery();
			if (rs.next()) {
				course = new ShortCourse();
				course.setId(courseId);
				course.setName(rs.getString("course_name"));
				course.setDuration(rs.getString("duration"));
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("start_date"));
				course.setStartDate(calendar);
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("end_date"));
				course.setEndDate(calendar);
				course.setTopicId(rs.getLong("topic_id"));
				course.setTeacherId(rs.getLong("teacher_id"));
				course.setStatusId(rs.getLong("status_id"));
			}
		} catch (NamingException | SQLException e) {
			throw e;
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return course;
	}
	
	/**
	 * Returns list of all courses.
	 * 
	 * @return {@code List<Course>}
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static List<Course> getAllCourses() throws NamingException, SQLException {
		List<Course> coursesList = null;
		Course course = null;
		Calendar calendar = null;
		User teacher = null;
		DataSource dataSource = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(SELECT_ALL_COURSES);
			coursesList = new ArrayList<>();
			while (rs.next()) {
				course = new Course();
				course.setId(rs.getLong("course_id"));
				course.setName(rs.getString("course_name"));
				course.setDuration(rs.getString("duration"));
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("start_date"));
				course.setStartDate(calendar);
				calendar = Calendar.getInstance();
				calendar.setTime(rs.getDate("end_date"));
				course.setEndDate(calendar);
				course.setTopic(rs.getString("topic_name"));
				teacher = new User();
				teacher.setFirstName(rs.getString("first_name"));
				teacher.setMiddleName(rs.getString("middle_name"));
				teacher.setLastName(rs.getString("last_name"));
				course.setTeacher(teacher);
				course.setStatus(rs.getString("status"));
				
				coursesList.add(course);
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
	 * Deletes course by id.
	 * 
	 * @param courseId
	 * @return {@code true} if deleted was successful, otherwise {@code false} 
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static boolean deleteCourse(long courseId) throws SQLException, NamingException {
		boolean flag = false;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE_COURSE_BY_ID);
			statement.setLong(1,  courseId);
			statement.executeUpdate();
			flag = true;
		} catch (SQLException | NamingException e) {
			throw e;
		} finally {
			close(statement);
			close(connection);
		}
		return flag;
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
	 * Updates existing course.
	 * 
	 * @param courseId
	 * @param courseName
	 * @param duration
	 * @param startDate
	 * @param endDate
	 * @param topicId
	 * @param teacherId
	 * @param statusId
	 * @return {@code true} if update was successful, otherwise {@code false} 
	 * @throws SQLException
	 */
	public static boolean updateCourse(long courseId, String courseName, String duration,
			Date startDate, Date endDate, long topicId, long teacherId, long statusId) throws SQLException {
		boolean regFlag = false;
		DataSource ds = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			ds = DataSourceConfig.getDataSource();
			connection = ds.getConnection();
			statement = connection.prepareStatement(UPDATE_COURSE);
			statement.setString(1, courseName);
			statement.setString(2, duration);
			statement.setDate(3, startDate);
			statement.setDate(4, endDate);
			statement.setLong(5, topicId);
			statement.setLong(6, teacherId);
			statement.setLong(7, statusId);
			statement.setLong(8, courseId);
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
