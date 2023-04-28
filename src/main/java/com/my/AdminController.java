package com.my;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.dao.course.CourseDAO;
import com.my.entity.Course;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		try {
			switch (action) {
			case "teacherReg":
				req.setAttribute("adminPage", true);
				req.getRequestDispatcher("users/sign_up.jsp").forward(req, resp);
				break;
			case "getAllCourses":
				getAllCourses(req);
				req.getRequestDispatcher("users/role/admin.jsp").forward(req, resp);
				break;
			case "edit":
				resp.getOutputStream().print(action);
				break;
			case "delete":
				deleteCourse(req);
				getAllCourses(req);
				req.getRequestDispatcher("users/role/admin.jsp").forward(req, resp);
				break;
			case "addCourse":
				resp.getOutputStream().print(action);
				break;
			}
		} catch (ServletException | SQLException | IOException | NamingException e) {
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

	private void deleteCourse(HttpServletRequest req) throws SQLException, NamingException {
		long courseId = Long.parseLong(req.getParameter("courseId"));
		CourseDAO.deleteCourse(courseId);		
	}

	private void getAllCourses(HttpServletRequest req) throws NamingException, SQLException {
		List<Course> allCourses = CourseDAO.getAllCourses();
		req.setAttribute("allCourses", allCourses);
	}

}
