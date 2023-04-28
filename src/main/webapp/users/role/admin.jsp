<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div>
		<div>
			<ul>
				<li><a href="/elective/admin?action=teacherReg">Teacher registration</a></li>
				<li><a href="/elective/admin?action=getAllCourses">Course management</a></li>
				<li><a href="">Student management</a></li>
			</ul>
		</div>
		<div>
			<c:if test="${allCourses != null}">
				<hr>
				<h4>All courses</h4>
				<table>
					<thead>
						<tr>
							<th>Name</th>
							<th>Duration</th>
							<th>Start date</th>
							<th>End date</th>
							<th>Topic</th>
							<th>Teacher</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="course" items="${allCourses}">
							<tr>
								<td><c:out value="${course.name}" /></td>
								<td><c:out value="${course.duration}" /></td>
								<td><c:out value="${course.formatStartDate}" /></td>
								<td><c:out value="${course.formatEndDate}" /></td>
								<td><c:out value="${course.topic}" /></td>
								<td><c:out value="${course.teacher}" /></td>
								<td><c:out value="${course.status}" /></td>
								<td><a href="/elective/admin?action=edit">Edit</a></td>
								<td><a href="/elective/admin?action=delete&courseId=<c:out value="${course.id}"/>">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div>
					<form action="/elective/admin" method="get">
						<button value="addCourse" name="action">Add course</button>
					</form>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>