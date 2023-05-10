package com.my;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.dao.course.CourseDAO;
import com.my.dao.course.StatusDAO;
import com.my.dao.course.TopicDAO;
import com.my.dao.data.StudentDAO;
import com.my.dao.data.TeacherDAO;
import com.my.entity.Course;
import com.my.entity.ShortCourse;
import com.my.entity.Status;
import com.my.entity.Topic;
import com.my.entity.User;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getParameter("action");
		boolean isNewCourse = Boolean.getBoolean(req.getParameter("isNewCourse"));
		
		try {
			switch (action) {
			case "teacherReg":
				req.setAttribute("adminPage", true);
				req.getRequestDispatcher("users/sign_up.jsp").forward(req, resp);
				break;
			case "getAllStudents":
				getAllStudents(req, resp);
				break;
			case "getAllCourses":
				getAllCourses(req, resp);
				break;
			case "edit":
				editCourse(req);
				getBaseInformation(req, resp);
				break;
			case "delete":
				deleteCourse(req);
				getAllCourses(req, resp);
				break;
			case "addCourse":
				getBaseInformation(req, resp);
				break;
			case "save":
				if (isNewCourse) {
					addNewCourse(req);
					getBaseInformation(req, resp);
				} else {
					updateCourse(req);
					getAllCourses(req, resp);
				}
				break;
			case "locked":
			case "unlocked":
				changeStatus(req);
				getAllStudents(req, resp);
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

	private void changeStatus(HttpServletRequest req) throws SQLException {
		long userId = Long.parseLong(req.getParameter("studentId"));
		String toStatus = req.getParameter("action");
		boolean statusFlag = StudentDAO.changeStatus(userId, toStatus);
		req.setAttribute("statusFlag", statusFlag);
		req.setAttribute("studentIdFlag", userId);
	}

	private void getAllStudents(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NamingException, ServletException, IOException {
		List<User> allStudents = StudentDAO.getStudents();
		req.setAttribute("allStudents", allStudents);
		req.getRequestDispatcher("users/role/admin.jsp").forward(req, resp);
	}

	private void updateCourse(HttpServletRequest req) throws SQLException {
		long courseId = Long.parseLong(req.getParameter("courseId"));
		String courseName = req.getParameter("name");
		String duration = req.getParameter("duration");
		Date startDate = Date.valueOf(req.getParameter("start_date"));
		Date endDate = Date.valueOf(req.getParameter("end_date"));
		long topicId = Long.parseLong(req.getParameter("topic"));
		long teacherId = Long.parseLong(req.getParameter("teacher"));
		long statusId = Long.parseLong(req.getParameter("status"));
		CourseDAO.updateCourse(courseId, courseName, duration, startDate, endDate, topicId, teacherId, statusId);
	}

	private void editCourse(HttpServletRequest req) throws NamingException, SQLException {
		long courseId = Long.parseLong(req.getParameter("courseId"));
		ShortCourse shortCourse = CourseDAO.getCourseById(courseId);
		req.setAttribute("shortCourse", shortCourse);
	}

	private void addNewCourse(HttpServletRequest req) throws SQLException {
		String courseName = req.getParameter("name");
		String duration = req.getParameter("duration");
		Date startDate = Date.valueOf(req.getParameter("start_date"));
		Date endDate = Date.valueOf(req.getParameter("end_date"));
		long topicId = Long.parseLong(req.getParameter("topic"));
		long teacherId = Long.parseLong(req.getParameter("teacher"));
		long statusId = Long.parseLong(req.getParameter("status"));
		long courseId = CourseDAO.insertNewCourse(courseName, duration, startDate, endDate, topicId, teacherId, statusId);
		req.setAttribute("courseId", courseId);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void getBaseInformation(HttpServletRequest req, HttpServletResponse resp) throws NamingException, SQLException, ServletException, IOException {
		List<Topic> topicList = TopicDAO.getTopics();
		List<User> teacherList = TeacherDAO.getTeachers();
		List<Status> statusList = StatusDAO.getStatuses();
		req.setAttribute("topicList", topicList);
		req.setAttribute("teacherList", teacherList);
		req.setAttribute("statusList", statusList);
		req.getRequestDispatcher("course/editcourse.jsp").forward(req, resp);
	}

	private void deleteCourse(HttpServletRequest req) throws SQLException, NamingException {
		long courseId = Long.parseLong(req.getParameter("courseId"));
		CourseDAO.deleteCourse(courseId);		
	}

	private void getAllCourses(HttpServletRequest req, HttpServletResponse resp) throws NamingException, SQLException, ServletException, IOException {
		List<Course> allCourses = CourseDAO.getAllCourses();
		req.setAttribute("allCourses", allCourses);
		req.getRequestDispatcher("users/role/admin.jsp").forward(req, resp);
	}

}
