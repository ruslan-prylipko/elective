<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div>
		<div>
			<div>
				<h3>Courses</h3>
				<a href="/elective/student?courses=my">My</a> <a
					href="/elective/student?courses=available">Available</a>
			</div>
		</div>
		<div>
			<c:if test="${myCoursesList != null}">
				<hr>
				<h4>My</h4>
				<table>
					<thead>
						<tr>
							<th>Name</th>
							<th>Duration</th>
							<th>Registration date</th>
							<th>Start date</th>
							<th>End date</th>
							<th>Topic</th>
							<th>Teacher</th>
							<th>Mark</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="course" items="${myCoursesList}">
							<tr>
								<td><c:out value="${course.name}" /></td>
								<td><c:out value="${course.duration}" /></td>
								<td><c:out value="${course.formatRegistrationDate}" /></td>
								<td><c:out value="${course.formatStartDate}" /></td>
								<td><c:out value="${course.formatEndDate}" /></td>
								<td><c:out value="${course.topic}" /></td>
								<td><c:out value="${course.teacher}" /></td>
								<td><c:out value="${course.mark}" /></td>
								<td><c:out value="${course.status}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<div>
			<c:if test="${availableCoursesList != null}">
				<hr>
				<h4>Available</h4>
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
						<c:forEach var="course" items="${availableCoursesList}">
							<tr>
								<td><c:out value="${course.name}" /></td>
								<td><c:out value="${course.duration}" /></td>
								<td><c:out value="${course.formatStartDate}" /></td>
								<td><c:out value="${course.formatEndDate}" /></td>
								<td><c:out value="${course.topic}" /></td>
								<td><c:out value="${course.teacher}" /></td>
								<td><c:out value="${course.status}" /></td>
								<td><a href="/elective/student?courses=available&reg=<c:out value='${course.id}'/>">Registration</a></td>
								<c:if test="${courseId == course.id}">
									<td><c:out value="${regFlag}" /></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</body>
</html>