<%@ page import="com.my.entity.User"%>
<div>
	<%
	User user = (User) session.getAttribute("user");
	%>
	<div>
		<div><%=user.getUsername()%></div>
		<div><%=user.getFirstName()%></div>
		<div><%=user.getLastName()%></div>
		<div><%=user.getMiddleName()%></div>
		<div><%=user.getEmail()%></div>
		<div><%=user.getRole()%></div>
	</div>
	<hr>
</div>