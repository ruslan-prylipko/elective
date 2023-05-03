package com.my.dao.course;

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
import com.my.entity.Status;

public class StatusDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String SELECT_STATUSES =
			"SELECT id, status FROM status;";
	
	private StatusDAO() {
	}

	/**
	 * Returns list of statuses.
	 * 
	 * @return {@code List<Status>}
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static List<Status> getStatuses() throws NamingException, SQLException {
		List<Status> statusList = null;
		Status status = null;
		DataSource dataSource = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(SELECT_STATUSES);
			statusList = new ArrayList<>();
			while (rs.next()) {
				status = new Status();
				status.setId(rs.getLong("id"));
				status.setName(rs.getString("status"));
				statusList.add(status);
			}
		} catch (NamingException | SQLException e) {
			throw e;
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return statusList;
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
