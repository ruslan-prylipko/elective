package com.my.dao.manager;

import com.my.dao.data.DataManipulation;

/**
 * Factory method to get DAO object
 * @see UserManager
 * @see CourseManager
 */
public abstract class ManagerDB {
	/**
	 * These constant defines which of type ManagerDB will be created
	 */
	public static final int USER_TYPE = 1;
	public static final int COURCE_TYPE = 2;

	/**
	 * Factory method uses parameter type to defined
	 * of ManagerDB type which  will be returned
	 * 
	 * @param  type
	 * @return ManagerDB corresponding to type parameter
	 * @throws IllegalArgumentException - if passed incorrect type
	 */
	public static ManagerDB getInstance(int type) throws IllegalArgumentException {
		if (type == USER_TYPE) {
			return new UserManager();
		}
		if (type == COURCE_TYPE) {
			return new CourseManager();
		}
		throw new IllegalArgumentException();
	}
	
	public abstract DataManipulation<?> getDataManipulation();

}
