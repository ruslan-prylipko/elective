package com.my.db;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DataSourceConfig {
	private static final Logger LOGGER = LogManager.getLogger();
	
	private DataSourceConfig() {
		
	}
	
	/**
	 * Configuring a JNDI DataSource with a DB connection pool.
	 * @return DataSourse object
	 * @throws NamingException if configuration error occurred
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static DataSource getDataSource() throws NamingException {
		Context initContext = null;
		Context envContext  = null;
		DataSource ds = null;	
		try {
			initContext = new InitialContext(); 							// NamingException
			envContext  = (Context)initContext.lookup("java:/comp/env");	// NamingException
			ds = (DataSource)envContext.lookup("jdbc/TestDB");				// NamingException
		} catch (NamingException e) {
			NamingException exception = new NamingException("Can not connect to database!");
			exception.initCause(e);
			LOGGER.error(exception.getExplanation());
			LOGGER.debug(e);
			throw exception;
		}
		return ds;
	}
	
	// For testing, this method must be uncommented, and the method above should be commented out
	/*public static DataSource getDataSource() throws NamingException {
		StringBuilder authBuilder = null;
		try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(java.nio.file.Paths.get("src/test/resources/auth/db", "test-db.txt").toString()))) {
		String line;
		authBuilder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			authBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		com.mysql.cj.jdbc.MysqlDataSource dataSource = new com.mysql.cj.jdbc.MysqlDataSource();
		dataSource.setURL(authBuilder.toString());
		
		return dataSource;
	}*/
}
