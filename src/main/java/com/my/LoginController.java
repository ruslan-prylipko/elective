package com.my;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.dao.data.LoginException;
import com.my.dao.data.UserLogin;
import com.my.datacontroll.PreparedData;
import com.my.entity.Login;
import com.my.entity.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final Logger LOGGER = LogManager.getLogger();

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		switch (action) {
		case "logout":
			HttpSession session = req.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			resp.sendRedirect(req.getContextPath() + "/users/sign_in.jsp");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailOrUsername = req.getParameter("username");
		String password = req.getParameter("password");

		User user;
		try {
			user = authenticate(emailOrUsername, password);
			HttpSession session = req.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("user", user);
			switch (user.getRole()) {
			case "student":
				resp.sendRedirect("users/role/student.jsp");
				break;
			case "teacher":
				resp.sendRedirect("teacher");
				break;
			case "admin":
				resp.sendRedirect("users/role/admin.jsp");
				break;
			}
		} catch (IllegalArgumentException | SQLException | NamingException | LoginException e) {
			LOGGER.error(e.getMessage());
			LOGGER.debug(e);
			req.setAttribute("exception", e.getMessage());
			try {
				req.getRequestDispatcher("errors/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				LOGGER.error(ex.getMessage());
				LOGGER.debug(ex);
				throw ex;
			}
		}
	}

	/**
	 * User authenticate.
	 * 
	 * @param emailOrUsername - String type
	 * @param password        - String type
	 * @return User object
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 * @throws NamingException
	 * @throws LoginException
	 */
	private User authenticate(String emailOrUsername, String password)
			throws IllegalArgumentException, SQLException, NamingException, LoginException {
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
		} catch (IllegalArgumentException | SQLException | LoginException e) {
			LOGGER.error(e.getMessage());
			LOGGER.debug(e);
			throw e;
		}
		return user;
	}
}
