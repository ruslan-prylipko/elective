<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<div>
	<h4 class="text-header">All students</h4>
	<table class="table">
		<thead>
			<tr>
				<th>Username</th>
				<th>Full name</th>
				<th>Email</th>
				<th>Status</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="student" items="${allStudents}">
				<tr>
					<td><c:out value="${student.username}" /></td>
					<td><c:out value="${student}" /></td>
					<td><c:out value="${student.email}" /></td>
					<td><c:out value="${student.status}" /></td>
					<c:if test="${student.status == 'unlocked'}">
						<td><a
							href="/elective/admin?action=locked&studentId=<c:out value="${student.id}"/>">Lock</a></td>
					</c:if>
					<c:if test="${student.status == 'locked'}">
						<td><a
							href="/elective/admin?action=unlocked&studentId=<c:out value="${student.id}"/>">Unlock</a></td>
					</c:if>
					<c:if test="${student.id == studentIdFlag}">
						<!--<c:if test="${statusFlag}">
							<td><c:out value="${statusFlag}" /></td>
						</c:if>-->
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>