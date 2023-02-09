<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Sign up</title>
	</head>
	<body>
		<div>
			<form action="main" accept-charset="UTF-8" method="post">
			<div>
				<div>
					<label>First name</label><p>
					<input required="required" title="This field is required" type="text">
				</div>
				<div>
					<label>Middle name</label><p>
					<input required="required" title="This field is required" type="text">
				</div>
				<div>
					<label>Last name</label><p>
					<input required="required" title="This field is required" type="text">
				</div>
				</div>
				<div>
					<div>
						<label>Username</label><p>
						<input required="required" pattern="[a-zA-Z0-9_\.][a-zA-Z0-9_\-\.]*[a-zA-Z0-9_\-]|[a-zA-Z0-9_]"
						title="Please create a username with only alphanumeric characters" type="text">
					</div>
					<div>
						<label>Email</label><p>
						<input required="required" title="Please provide a valid email address" type="text">
					</div>
					<div>
						<label>Password</label><p>
						<input required="required" pattern=".{8,}" title="Minimum length is 8 characters" type="password">
					</div>
					<div>
						<input name="commit" value="Register" type="submit">
					</div>
				</div>
			</form>
		</div>
	</body>
</html>