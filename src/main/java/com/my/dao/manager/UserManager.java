package com.my.dao.manager;

import com.my.dao.data.DataManipulation;
import com.my.dao.data.UserData;
import com.my.entity.User;

public class UserManager extends ManagerDB {

	@Override
	public DataManipulation<User> getDataManipulation() {
		return new UserData();
	}
}
