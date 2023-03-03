package com.my.dao.data;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

/**
 * General interface for DAO
 * 
 * @param <T> - the type of object for DAO
 * @see UserData
 * @see CourseData
 */
public interface DataManipulation<T> {

	boolean create(T t) throws SQLException;
	T select(T t) throws SQLException, NamingException;
	List<T> selectAll();
	boolean delete(T t) throws SQLException;
	boolean update(T t) throws SQLException;
	
}
