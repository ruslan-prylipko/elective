<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Sign in</title>
	</head>
	<body>
		<div>
			<form action="../login" accept-charset="UTF-8" method="post">
				<div>
					<label>Username or email</label><p>
					<input name="username" required="required" title="This field is required." type="text">
				</div>
				<div>
					<label>Password</label><p>
					<input name="password" required="required" title="This field is required." type="password">
				</div>
				<div>
					<button name="button" type="submit">Sign in</button>
				</div>
			</form>
		</div>
		<div>
			<p>
			Don't have an account yet?
			<a href="sign_up.jsp">Register now</a>
			</p>
		</div>
	</body>
</html>
