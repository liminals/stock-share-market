<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game Page</title>
</head>
<body>
	<%
		String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	%>

	<h1>Game Page</h1>
	<h3 id="currentTurn">Current Turn</h3>
	<h4>Stocks</h4>
	
	<button onclick="updateStockPrices()">Load</button>
	<div id="stockDashboard">
		<label>Search Stocks </label><input type="text" id="searchStock">
		<div id="serachResults"></div>
		<div id="stockGraph"></div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script type="text/javascript">
		var serviceUrl = "<%= serviceUrl%>";
	</script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/script.js"%>"></script>
</body>
</html>