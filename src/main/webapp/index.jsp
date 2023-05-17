<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<link rel="stylesheet"
	href=<%=request.getContextPath() + "/users/role/css/main.css"%>>
</head>
<body>
	<div class="edit-course-box">
		<div>
			<h1 class="text-header text-header-elective">Welcome to elective!</h1>
		</div>
		<div class="elective-button-box">
			<a class="button-link" href="users/sign_in.jsp">Sign In</a>
			<a class="button-link" href="users/sign_up.jsp">Register now</a>
		</div>
	</div>
</body>
</html>