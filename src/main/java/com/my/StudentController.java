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

@WebServlet("/student")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1657209444860118137L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			getCoursesList(req, resp);
		} catch (IllegalArgumentException | SQLException | NamingException e) {
			req.setAttribute("exception", e.getMessage());
			try {
				req.getRequestDispatcher("errors/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				throw ex;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void getCoursesList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NamingException, SQLException {
		long userId = (Long)req.getSession(false).getAttribute("userId");
		String courses = req.getParameter("courses");
		if (courses.equalsIgnoreCase("my")) {
			List<UserCourse> myCoursesList = CourseDAO.getUserCourses(userId);
			req.setAttribute("myCoursesList", myCoursesList );
		}
		if (courses.equalsIgnoreCase("available")) {
			List<Course> availableCoursesList = CourseDAO.getAvailableCourses(userId);
			req.setAttribute("availableCoursesList", availableCoursesList );
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("users/role/student.jsp");
		dispatcher.forward(req, resp);
	}
	
}
