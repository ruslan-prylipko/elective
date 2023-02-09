package com.my.dao.data;

import java.sql.SQLException;
import java.util.List;

public interface DataManipulation<T> {

	boolean create(T t) throws SQLException;
	T select(T t) throws SQLException;
	List<T> selectAll();
	boolean delete(T t) throws SQLException;
	boolean update(T t) throws SQLException;
	
}
