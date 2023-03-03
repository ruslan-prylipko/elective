package com.my.dao.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.db.DataSourceConfig;
import com.my.entity.User;

public class UserData implements DataManipulation<User> {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String SELECT_USER = 
			"SELECT id, username, first_name, middle_name, last_name, email FROM user WHERE username = ?";
	
	public UserData() {
		
	}
	
	@Override
	public boolean create(User t) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User select(User t) throws SQLException, NamingException {
		DataSource dataSource;
		try {
			dataSource = DataSourceConfig.getDataSource();
		} catch (NamingException e) {
			LOGGER.error(e.getMessage());
			LOGGER.debug(e);
			throw e;
		}
		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparableStatement = connection.prepareStatement(SELECT_USER)) {
			preparableStatement.setString(1, t.getUsername());
			ResultSet rs = preparableStatement.executeQuery();
			
			while (rs.next()) {
				t.setId(rs.getLong("id"));
				t.setFirstName(rs.getString("first_name"));
				t.setMiddleName(rs.getString("middle_name"));
				t.setLastName(rs.getString("last_name"));
				t.setEmail(rs.getString("email"));
			}
		}
		return t;
	}

	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(User t) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User t) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
