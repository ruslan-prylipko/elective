package com.my;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dao.data.DataManipulation;
import com.my.dao.manager.ManagerDB;
import com.my.entity.User;

@WebServlet("/main")
public class Main extends HttpServlet {

	private static final long serialVersionUID = -610811780024982546L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getOutputStream().println(getServletConfig().toString());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = new User(username, password);
		ManagerDB managerDB = ManagerDB.getInstance(ManagerDB.USER_TYPE);
		DataManipulation<User> userData = (DataManipulation<User>) managerDB.getDataManipulation();
		try {
			userData.select(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.getOutputStream().println("Hello" + " " + user);
	}
}
