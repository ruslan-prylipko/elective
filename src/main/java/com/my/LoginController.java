package com.my;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dao.data.UserLogin;
import com.my.datacontroll.PreparedData;
import com.my.entity.Login;
import com.my.entity.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailOrUsername = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = authenticate(emailOrUsername, password);
		resp.getOutputStream().println("Hello" + " " + user);
	}

	private User authenticate(String emailOrUsername, String password) {
		Login login = new Login();
		User user = null;
		try {
			if (PreparedData.isEmail(emailOrUsername)) {
				login.setEmail(emailOrUsername);
				login.setEmailFlag(true);
			} else if (PreparedData.isUsername(emailOrUsername)) {
				login.setUsername(emailOrUsername);
			}
			if (PreparedData.passwordValidation(password)) {
				login.setPassword(password);
			}
			user = UserLogin.select(login);
		} catch (IllegalArgumentException e) {
			System.out.println("Error!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		return user;
	}
	
}
