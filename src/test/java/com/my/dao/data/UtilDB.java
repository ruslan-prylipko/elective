package com.my.dao.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilDB {
	private static final String DROP_TABLE_JOURNAL = "DROP TABLE IF EXISTS journal;";
	private static final String DROP_TABLE_COURSE = "DROP TABLE IF EXISTS course;";
	private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS user;";
	private static final String DROP_TABLE_STATUS = "DROP TABLE IF EXISTS status;";
	private static final String DROP_TABLE_TOPIC = "DROP TABLE IF EXISTS topic;";
	private static final String DROP_TABLE_ROLE = "DROP TABLE IF EXISTS role;";

	// insert role
	private static final String INSERT_ROLE_ADMIN = "INSERT INTO role (id, name) VALUES (DEFAULT, \"admin\");";
	private static final String INSERT_ROLE_TEACHER = "INSERT INTO role (id, name) VALUES (DEFAULT, \"teacher\");";
	private static final String INSERT_ROLE_STUDENT = "INSERT INTO role (id, name) VALUES (DEFAULT, \"student\");";
	
	/**
	 * Creates tables in test database
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void createDB() throws SQLException, IOException {
		Connection connection = DriverManager.getConnection(load(Paths.get("src/test/resources/auth/db", "test-db.txt").toString()));
		Statement statement = connection.createStatement();
		statement.execute(load(Paths.get("src/test/resources/sql", "create_teble_topic.sql").toString()));
		statement.execute(load(Paths.get("src/test/resources/sql", "create_table_status.sql").toString()));
		statement.execute(load(Paths.get("src/test/resources/sql", "create_table_role.sql").toString()));
		statement.execute(load(Paths.get("src/test/resources/sql", "create_table_user.sql").toString()));
		statement.execute(load(Paths.get("src/test/resources/sql", "create_table_course.sql").toString()));
        statement.execute(load(Paths.get("src/test/resources/sql", "create_table_journal.sql").toString()));
        statement.close();
        connection.close();
	}
	
	/**
	 * Deletes tables in test database
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void dropDB() throws SQLException, IOException {
		Connection connection = DriverManager.getConnection(load(Paths.get("src/test/resources/auth/db", "test-db.txt").toString()));
		Statement statement = connection.createStatement();
		statement.execute(DROP_TABLE_JOURNAL);
		statement.execute(DROP_TABLE_COURSE);
		statement.execute(DROP_TABLE_USER);
		statement.execute(DROP_TABLE_STATUS);
		statement.execute(DROP_TABLE_TOPIC);
		statement.execute(DROP_TABLE_ROLE);
		statement.close();
		connection.close();		
	}
	
	
	/**
	 * Fills the table 'role'
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void fillRoleTable() throws SQLException, IOException {
		Connection connection = DriverManager.getConnection(load(Paths.get("src/test/resources/auth/db", "test-db.txt").toString()));
		Statement statement = connection.createStatement();
		statement.execute(INSERT_ROLE_ADMIN);
        statement.execute(INSERT_ROLE_TEACHER);
        statement.execute(INSERT_ROLE_STUDENT);
		statement.close();
		connection.close();	
	}
	
	public static Connection getConnection() throws SQLException, IOException {
		return DriverManager.getConnection(load(Paths.get("src/test/resources/auth/db", "test-db.txt").toString()));
	}
	
	private static String load(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			StringBuilder scriptBuilder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
			    scriptBuilder.append(line);
			}
			return scriptBuilder.toString();
		}
	}

	/**
	 * Insert user to test database
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void insertUser() throws SQLException, IOException {
		Connection connection = DriverManager.getConnection(load(Paths.get("src/test/resources/auth/db", "test-db.txt").toString()));
		Statement statement = connection.createStatement();
		statement.execute(load(Paths.get("src/test/resources/sql", "insert-user.sql").toString()));
		statement.close();
		connection.close();	
	}

}
