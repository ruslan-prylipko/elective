<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
<link rel="stylesheet" href=<%=request.getContextPath() + "/users/role/css/header.css"%>>
<link rel="stylesheet" href=<%=request.getContextPath() + "/users/role/css/main.css"%>>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="page-box">
		<div>
			<a class="button-link" href="/elective/admin?action=teacherReg">Teacher registration</a>
			<a class="button-link" href="/elective/admin?action=getAllCourses">Course management</a>
			<a class="button-link" href="/elective/admin?action=getAllStudents">Student management</a>
		</div>
		<div>
			<c:if test="${allCourses != null}">
				<h4 class="text-header text-child-header">All courses</h4>
				<table class="table">
					<thead>
						<tr>
							<th>Name</th>
							<th>Duration</th>
							<th>Start date</th>
							<th>End date</th>
							<th>Topic</th>
							<th>Teacher</th>
							<th>Status</th>
							<th></th>
							<th></th>
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
								<td><a
									href="/elective/admin?action=edit&courseId=<c:out value="${course.id}"/>">Edit</a></td>
								<td><a
									href="/elective/admin?action=delete&courseId=<c:out value="${course.id}"/>">Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page-box">
					<form action="/elective/admin" method="get">
						<button class="button-for-admin" value="addCourse" name="action">Add course</button>
					</form>
				</div>
			</c:if>
			<c:if test="${allStudents != null}">
				<jsp:include page="../manager/usermanager.jsp"></jsp:include>
			</c:if>
		</div>
	</div>
</body>
</html>