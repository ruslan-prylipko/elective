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
import com.my.entity.Topic;

public class TopicDAO {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String SELECT_TOPICS =
			"SELECT id, name FROM topic;";
	
	private TopicDAO() {
	}

	/**
	 * Returns list of topics.
	 * 
	 * @return {@code List<Topic>}
	 * @throws NamingException
	 * @throws SQLException
	 */
	public static List<Topic> getTopics() throws NamingException, SQLException {
		List<Topic> topicList = null;
		Topic topic = null;
		DataSource dataSource = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			dataSource = DataSourceConfig.getDataSource();
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(SELECT_TOPICS);
			topicList = new ArrayList<>();
			while (rs.next()) {
				topic = new Topic();
				topic.setId(rs.getLong("id"));
				topic.setName(rs.getString("name"));
				topicList.add(topic);
			}
		} catch (NamingException | SQLException e) {
			throw e;
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return topicList;
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
