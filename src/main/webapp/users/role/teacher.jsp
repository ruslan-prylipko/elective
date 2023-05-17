<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher</title>
<link rel="stylesheet" href=<%=request.getContextPath() + "/users/role/css/header.css"%>>
<link rel="stylesheet" href=<%=request.getContextPath() + "/users/role/css/main.css"%>>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="main-block">
		<div>
			<h3 class="text-header">Journal</h3>
		</div>
		<div>
			<c:if test="${coursesList != null}">
				<table class="table">
					<thead>
						<tr>
							<th>Student</th>
							<th>Name</th>
							<th>Duration</th>
							<th>Registration date</th>
							<th>Start date</th>
							<th>End date</th>
							<th>Topic</th>
							<th>Mark</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="course" items="${coursesList}">
							<tr>
								<td><c:out value="${course.student}" /></td>
								<td><c:out value="${course.name}" /></td>
								<td><c:out value="${course.duration}" /></td>
								<td><c:out value="${course.formatRegistrationDate}" /></td>
								<td><c:out value="${course.formatStartDate}" /></td>
								<td><c:out value="${course.formatEndDate}" /></td>
								<td><c:out value="${course.topic}" /></td>
								<td><form action="teacher" method="post">
									<input type="hidden" value="<c:out value="${course.studentId}" />" name="studentId">
									<input type="hidden" value="<c:out value="${course.id}" />" name="courseId">
									<input type="text" value="<c:out value="${course.mark}" />" name="mark">
									<button>OK</button>
								</form></td>
								<td><c:out value="${course.status}" /></td>
								<td><c:if test="${courseId == course.id && studentId == course.studentId}">
									<td><c:out value="${markFlag}" /></td>
								</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</body>
</html>