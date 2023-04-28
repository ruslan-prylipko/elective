<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up</title>
</head>
<body>
	<div>
		<c:if test="${adminPage == null}">
			<c:set var="action" scope="session" value="../registration"/>
		</c:if>
		<c:if test="${adminPage == true}">
			<c:set var="action" scope="session" value="registration"/>
		</c:if>
			<form action="<c:out value="${action}"/>" accept-charset="UTF-8" method="post">
		<div>
			<div>
				<div>
					<label>First name</label>
					<p>
						<input name="first-name" required="required"
							title="This field is required" type="text">
				</div>
				<div>
					<label>Middle name</label>
					<p>
						<input name="middle-name" required="required"
							title="This field is required" type="text">
				</div>
				<div>
					<label>Last name</label>
					<p>
						<input name="last-name" required="required"
							title="This field is required" type="text">
				</div>
			</div>
			<div>
				<div>
					<label>Username</label>
					<p>
						<input name="username" required="required"
							pattern="^[A-Za-z][A-Za-z0-9_-]{7,29}$"
							title="Please create a username with only alphanumeric characters"
							type="text">
				</div>
				<div>
					<label>Email</label>
					<p>
						<input name="email" required="required"
							title="Please provide a valid email address" type="email">
				</div>
				<div>
					<label>Password</label>
					<p>
						<input name="password" required="required" pattern=".{8,}"
							title="Minimum length is 8 characters" type="password">
				</div>
				<c:if test="${adminPage == null}">
					<input type="hidden" value="student" name="role" />
				</c:if>
				<c:if test="${adminPage == true}">
					<input type="hidden" value="teacher" name="role" />
				</c:if>
				<div>
					<button name="button" type="submit">Register</button>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>