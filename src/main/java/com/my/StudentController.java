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

import com.my.entity.Course;
import com.my.entity.UserCourse;
import com.my.dao.course.CourseDAO;
import com.my.dao.course.JournalDAO;

@WebServlet("/student")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1657209444860118137L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			if (req.getParameter("courses") != null) {
				getCoursesList(req);
			}
			if (req.getParameter("reg") != null) {
				registrationOnCourse(req);
			}
			RequestDispatcher dispatcher = req.getRequestDispatcher("users/role/student.jsp");
			dispatcher.forward(req, resp);
		} catch (IllegalArgumentException | SQLException | NamingException e) {
			req.setAttribute("exception", e.getMessage());
			try {
				req.getRequestDispatcher("errors/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				throw ex;
			}
		} catch (NullPointerException e) {
			try {
				resp.sendRedirect(req.getContextPath() + "/users/sign_in.jsp");
			} catch (IOException ex) {
				throw ex;
			}
		}
	}

	private void registrationOnCourse(HttpServletRequest req) throws SQLException {
		long userId = (Long) req.getSession(false).getAttribute("userId");
		long courseId = Long.parseLong(req.getParameter("reg"));
		boolean flag = JournalDAO.registration(userId, courseId);
		req.setAttribute("courseId", courseId);
		req.setAttribute("regFlag", flag ? "Successful" : "Error");
	}

	private void getCoursesList(HttpServletRequest req) throws NamingException, SQLException, NullPointerException {
		long userId = (Long) req.getSession(false).getAttribute("userId");
		String courses = req.getParameter("courses");
		if (courses.equalsIgnoreCase("my")) {
			List<UserCourse> myCoursesList = CourseDAO.getUserCourses(userId);
			req.setAttribute("myCoursesList", myCoursesList);
		}
		if (courses.equalsIgnoreCase("available")) {
			List<Course> availableCoursesList = CourseDAO.getAvailableCourses(userId);
			req.setAttribute("availableCoursesList", availableCoursesList);
		}
	}

}
