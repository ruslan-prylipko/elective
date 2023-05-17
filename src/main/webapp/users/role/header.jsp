<%@ page import="com.my.entity.User"%>
<div class="main-box">
	<%
	User user = (User) session.getAttribute("user");
	%>
	<div class="user-info">
		<div class="user-info-item"><%=user.getRole()%></div>
		<div class="user-info-item"><%=user.getUsername()%></div>
		<div class="user-info-item"><%=user.getFirstName()%></div>
		<div class="user-info-item"><%=user.getLastName()%></div>
		<div class="user-info-item"><%=user.getMiddleName()%></div>
		<div class="user-info-item"><%=user.getEmail()%></div>
		<div class="button-item">
			<form action=<%=request.getContextPath() + "/login"%> method="get">
				<button class="button" value="logout" name="action">Sign out</button>
			</form>
		</div>
	</div>
</div>