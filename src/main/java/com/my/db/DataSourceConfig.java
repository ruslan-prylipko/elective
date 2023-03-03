package com.my.db;

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
}
