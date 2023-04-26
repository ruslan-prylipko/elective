package com.my.dao.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.Course;
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
	
	private CourseDAO() {
		
	}
	
	/**
	 * Return List of courses for which the student is registered.
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
	 * Return List of available courses for user.
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
