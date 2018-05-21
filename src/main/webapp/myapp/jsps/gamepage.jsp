<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game Page</title>
</head>
<body>
	<script type="text/javascript" src="<%= request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script type="text/javascript" src="<%= request.getContextPath() + "/myapp/js/script.js"%>"></script>
	
	<h1>Game Page</h1>
	<h3 id="currentTurn">Current Turn</h3>
</body>
</html>