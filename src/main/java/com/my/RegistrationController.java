package com.my;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.dao.data.UserRegistration;
import com.my.datacontroll.PreparedData;
import com.my.entity.RegistrationData;
import com.my.entity.User;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = -1569668315505148302L;
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationData registrationData = null;
		try {
			registrationData = getRegistrationData(req);
			UserRegistration.insert(registrationData);
			if (req.getParameter("role").equalsIgnoreCase("teacher")) {
				resp.sendRedirect("users/role/admin.jsp");
			} else {
				resp.sendRedirect("users/sign_in.jsp");
			}
		} catch (IllegalArgumentException e) {
			LOGGER.info(e.getMessage());
			LOGGER.debug(e);
			sendException(req, resp, e);
		} catch (SQLException | NamingException e) {
			LOGGER.error(e.getMessage());
			LOGGER.debug(e);
			sendException(req, resp, e);
		}
	}

	private void sendException(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
		req.setAttribute("exception", e.getMessage());
		try {
			req.getRequestDispatcher("errors/error.jsp").forward(req, resp);
		} catch (ServletException | IOException ex) {
			LOGGER.error(ex.getMessage());
			LOGGER.debug(ex);
			throw ex;
		}
	}

	private RegistrationData getRegistrationData(HttpServletRequest req) throws IllegalArgumentException {
		User user = new User();
		String username = req.getParameter("username");
		String firstName = req.getParameter("first-name");
		String middleName = req.getParameter("middle-name");
		String lastName = req.getParameter("last-name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		checkData(username, firstName, middleName, lastName, email, password);
		
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setRole(req.getParameter("role"));
		
		return new RegistrationData(user, password);
	}

	private void checkData(String username, String firstName, String middleName, String lastName, String email,
			String password) throws IllegalArgumentException {
		if (!PreparedData.isName(firstName)) {
			throwInfoException("Invalid first name!");
		}
		if (!PreparedData.isName(middleName)) {
			throwInfoException("Invalid middle name!");
		}
		if (!PreparedData.isName(lastName)) {
			throwInfoException("Invalid last name!");
		}
		if (!PreparedData.isUsername(username)) {
			throwInfoException("Invalid username!");
		}
		if (!PreparedData.isEmail(email)) {
			throwInfoException("Invalid email!");
		}
		if (!PreparedData.passwordValidation(password)) {
			throwInfoException("Invalid password!");
		}
	}
	
	/**
	 * Logging user errors by info level and throws exception.
	 * 
	 * @param message - error message
	 * @throws IllegalArgumentException which explanation user error
	 */
	private static void throwInfoException(String message) throws IllegalArgumentException {
		IllegalArgumentException e = new IllegalArgumentException(message);
		LOGGER.info(e.getMessage());
		LOGGER.debug(e);
		throw e;
	}
}
