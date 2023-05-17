<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<link rel="stylesheet"
	href=<%=request.getContextPath() + "/users/role/css/main.css"%>>
</head>
<body>
	<div class="edit-input-form">
		<h1 class="text-header text-header-sing-up">Error</h1>
		<h4 class="text-child-header">
			<%=(String) request.getAttribute("exception")%>
		</h4>
	</div>
</body>
</html>