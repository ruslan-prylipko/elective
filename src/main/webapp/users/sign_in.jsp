<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Sign in</title>
<link rel="stylesheet" href="css/sign-in.css">
</head>
<body>
	<div class="main-block">
		<div>
			<p id="top-form-text">
				Sign in if you already have an account.<br> If you have come
				here from the registration page, you must log in to activate your
				account.
			</p>
		</div>
		<div class="input-forms">
			<form action="../login" accept-charset="UTF-8" method="post">
				<div>
					<label>Username or email</label>
					<p>
						<input class="input-items" name="username" required="required"
							title="This field is required." type="text">
				</div>
				<div>
					<label>Password</label>
					<p>
						<input class="input-items" name="password" required="required"
							title="This field is required." type="password">
				</div>
				<div>
					<button class="input-items button" type="submit">Sign in</button>
				</div>
			</form>
			<div>
				<p>
					Don't have an account yet? <a href="sign_up.jsp">Register now</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>
