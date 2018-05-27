<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/rest/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Liminal Stock-Share-Market</title>
<link rel="stylesheet" href="./myapp/styles/main.css">
</head>
<body>
	<h1>Liminals</h1>
	<h2>Welcome user!</h2>
	<div id="container">
		<div id="createAccount">
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/RegisterPlayer" %>" method="post">
				<input type="text" name="username" placeholder="Username">
				<input type="password" name="password" placeholder="Password">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Register">
			</form>
		</div>
		<br/>
		<div id="loginAccount">
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/Login" %>" method="post">
				<input type="text" name="username" placeholder="Username">
				<input type="password" name="password" placeholder="Password">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Login">
			</form>
		</div>
	</div>
</body>
</html>