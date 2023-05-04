<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add/Edit Course</title>
</head>
<body>
	<div>
		<form action="/elective/admin" method="post">
			<div>
				<div>
					<label>Name</label>
					<p>
						<c:if test="${shortCourse == null}">
							<input name="name" required="required"
								title="This field is required" type="text" />
						</c:if>
						<c:if test="${shortCourse != null}">
							<input value="<c:out value="${shortCourse.name}"/>" name="name"
								required="required" title="This field is required" type="text" />
						</c:if>
				</div>
				<div>
					<label>Duration</label>
					<p>
						<select name="duration" required="required">
							<c:if test="${shortCourse == null}">
								<option value="">Select item</option>
							</c:if>
							<c:if test="${shortCourse != null}">
								<option value="<c:out value="${shortCourse.duration}"/>"><c:out
										value="${shortCourse.duration}" /></option>
							</c:if>
							<option value="1 weeks">1 weeks</option>
							<option value="2 weeks">2 weeks</option>
							<option value="3 weeks">3 weeks</option>
							<option value="4 weeks">4 weeks</option>
							<option value="5 weeks">5 weeks</option>
							<option value="6 weeks">6 weeks</option>
							<option value="7 weeks">7 weeks</option>
							<option value="8 weeks">8 weeks</option>
							<option value="9 weeks">9 weeks</option>
							<option value="10 weeks">10 weeks</option>
							<option value="11 weeks">11 weeks</option>
							<option value="12 weeks">12 weeks</option>
						</select>
				</div>
				<div>
					<div>
						<label>Start</label>
						<p>
							<c:if test="${shortCourse == null}">
								<input name="start_date" type="date" required="required"
									title="This field is required" />
							</c:if>
							<c:if test="${shortCourse != null}">
								<input value="<c:out value="${shortCourse.startDate}"/>"
									name="start_date" type="date" required="required"
									title="This field is required" />
							</c:if>
					</div>
					<div>
						<label>End</label>
						<p>
							<c:if test="${shortCourse == null}">
								<input name="end_date" type="date" required="required"
									title="This field is required" />
							</c:if>
							<c:if test="${shortCourse != null}">
								<input value="<c:out value="${shortCourse.endDate}"/>"
									name="end_date" type="date" required="required"
									title="This field is required" />
							</c:if>
					</div>
				</div>
				<div>
					<label>Topic</label>
					<p>
						<select name="topic" required="required">
							<c:if test="${shortCourse == null}">
								<option value="">Select item</option>
								<c:forEach var="t" items="${topicList}">
									<option value="<c:out value="${t.id}"/>"><c:out
											value="${t.name}" /></option>
								</c:forEach>
							</c:if>
							<c:if test="${shortCourse != null}">
								<c:forEach var="t" items="${topicList}">
									<c:if test="${t.id == shortCourse.topicId}">
										<option value="<c:out value="${t.id}"/>"><c:out
												value="${t.name}" /></option>
									</c:if>
								</c:forEach>
								<c:forEach var="t" items="${topicList}">
									<c:if test="${t.id != shortCourse.topicId}">
										<option value="<c:out value="${t.id}"/>"><c:out
												value="${t.name}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
						</select>
				</div>
				<div>
					<label>Teacher</label>
					<p>
						<select name="teacher" required="required">
							<c:if test="${shortCourse == null}">
								<option value="">Select item</option>
								<c:forEach var="t" items="${teacherList}">
									<option value="<c:out value="${t.id}"/>"><c:out
											value="${t}" /></option>
								</c:forEach>
							</c:if>
							<c:if test="${shortCourse != null}">
								<c:forEach var="t" items="${teacherList}">
									<c:if test="${t.id == shortCourse.teacherId}">
										<option value="<c:out value="${t.id}"/>"><c:out
												value="${t}" /></option>
									</c:if>
								</c:forEach>
								<c:forEach var="t" items="${teacherList}">
									<c:if test="${t.id != shortCourse.teacherId}">
										<option value="<c:out value="${t.id}"/>"><c:out
												value="${t}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
						</select>
				</div>
				<div>
					<label>Status</label>
					<p>
						<select name="status" required="required">
							<c:if test="${shortCourse == null}">
								<option value="">Select item</option>
								<c:forEach var="t" items="${statusList}">
									<option value="<c:out value="${t.id}"/>"><c:out
											value="${t}" /></option>
								</c:forEach>
							</c:if>
							<c:if test="${shortCourse != null}">
								<c:forEach var="t" items="${statusList}">
									<c:if test="${t.id == shortCourse.statusId}">
										<option value="<c:out value="${t.id}"/>"><c:out
												value="${t.name}" /></option>
									</c:if>
								</c:forEach>
								<c:forEach var="t" items="${statusList}">
									<c:if test="${t.id != shortCourse.statusId}">
										<option value="<c:out value="${t.id}"/>"><c:out
												value="${t.name}" /></option>
									</c:if>
								</c:forEach>
							</c:if>
						</select>
				</div>
				<div>
					<c:if test="${shortCourse != null}">
						<input type="hidden" value="false" name="isNewCourse" />
						<input type="hidden" value="<c:out value="${shortCourse.id}"/>" name="courseId" />
					</c:if>
					<c:if test="${shortCourse == null}">
						<input type="hidden" value="true" name="isNewCourse" />
					</c:if>
					<button value="save" name="action" type="submit">Save</button>
					<c:if test="${courseId != null}">
						<c:if test="${courseId != -1}">Successful</c:if>
						<c:if test="${courseId == -1}">Error</c:if>
					</c:if>
				</div>
			</div>
		</form>
	</div>
</body>
</html>