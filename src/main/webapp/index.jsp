<%@page import="java.util.Random"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Stock-Share-Market</title>
	<link rel="stylesheet" href="./myapp/styles/main.css">
</head>
<body id="mainBG">
	<h1>Liminals</h1>
	<h2>Stock-Share-Market Simulation Game</h2>
	<h2>Coming Soon!</h2>

	<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/Main" %>" method="post">
		<input type="submit" value="Play!!!">
	</form>
</body>
</html>