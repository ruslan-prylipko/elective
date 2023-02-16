package com.my.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceConfig {
	
	private DataSourceConfig() {
		
	}
	
	public static DataSource getDataSource() {
		
		Context initContext = null;
		Context envContext  = null;
		DataSource ds = null;	
		
		try {
			initContext = new InitialContext(); 							// NamingException
			envContext  = (Context)initContext.lookup("java:/comp/env");	// NamingException
			ds = (DataSource)envContext.lookup("jdbc/TestDB");				// NamingException
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.out.println("NamingException");
		}
		
		return ds;
	}
}
