package com.my.dao.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.User;

public class TeacherDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String SELECT_TEACHERS =
			"select id, first_name, middle_name, last_name "
			+ "from user "
			+ "where role_id = (select id from role where name=\"teacher\");";
	
	private TeacherDAO() {
	}

	/**
	 * Returns list of teachers.
	 * 
	 * @return {@code List<User>}
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static List<User> getTeachers() throws NamingException, SQLException {
		List<User> teacherList = null;
		User teacher = null;
		DataSource dataSource = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(SELECT_TEACHERS);
			teacherList = new ArrayList<>();
			while (rs.next()) {
				teacher = new User();
				teacher.setId(rs.getLong("id"));
				teacher.setFirstName(rs.getString("first_name"));
				teacher.setMiddleName(rs.getString("middle_name"));
				teacher.setLastName(rs.getString("last_name"));
				teacherList.add(teacher);
			}
		} catch (NamingException | SQLException e) {
			throw e;
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return teacherList;
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
