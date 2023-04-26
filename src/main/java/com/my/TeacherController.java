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

import com.my.dao.course.JournalDAO;
import com.my.entity.UserCourse;

@WebServlet("/teacher")
public class TeacherController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			setMark(req, resp);
			doGet(req, resp);
		} catch (ServletException | IOException e) {
			throw e;
		}
	}

	private void setMark(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int mark = Integer.parseInt(req.getParameter("mark"));
		long studentId = Long.parseLong(req.getParameter("studentId"));
		long courseId = Long.parseLong(req.getParameter("courseId"));
		try {
			boolean markFlag = JournalDAO.setStudentMark(courseId, studentId, mark);
			req.setAttribute("courseId", courseId);
			req.setAttribute("studentId", studentId);
			req.setAttribute("markFlag", markFlag ? "Successful" : "Error");
		} catch (IllegalArgumentException | SQLException e) {
			req.setAttribute("exception", e.getMessage());
			try {
				req.getRequestDispatcher("errors/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				throw ex;
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			getCoursesList(req);
			RequestDispatcher dispatcher = req.getRequestDispatcher("users/role/teacher.jsp");
			dispatcher.forward(req, resp);
		} catch (IllegalArgumentException | SQLException | NamingException e) {
			req.setAttribute("exception", e.getMessage());
			try {
				req.getRequestDispatcher("errors/error.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				throw ex;
			}
		}
	}

	private void getCoursesList(HttpServletRequest req) throws NamingException, SQLException {
		long userId = (Long) req.getSession(false).getAttribute("userId");
		List<UserCourse> coursesList = JournalDAO.getTeacherCourses(userId);
		req.setAttribute("coursesList", coursesList);
	}

}
