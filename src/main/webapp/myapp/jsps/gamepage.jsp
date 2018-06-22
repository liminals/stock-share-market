<%@page import="com.liminal.model.GameJoinData"%>
<%@page import="com.liminal.controller.JoinHostedGame"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.liminal.model.ClientTurn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%! ClientTurn ct; 
	GameJoinData gjd;
	JSONObject jo;
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Game Page</title>
	<link rel="stylesheet" href="../styles/gamepage.css">
	<link rel="stylesheet" href="<%= request.getContextPath() + "/myapp/styles/gamepage.css" %>">
</head>
<body>
	<%
		String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	%>

	<h1>Game Page</h1>
	<h3 id="currentTurn">Current Turns</h3>
	<h4>Stocks</h4>
	
	<!-- <button onclick="updateNewStockPrices()">Update</button> -->
	<button onclick="canRequestData()">Update</button>
	<button onclick="countTurns()">Next</button>
	<div id="stockDashboard">
		<div id="searchStockDiv">
			<label>Search Stocks </label>
			<input type="text" id="searchStock">
			<div id="searchResults"></div>
		</div>
		<span id="eventDetails">
		</span>
		<canvas id="stockGraph"></canvas>
	</div>
	
	<% 	ct = (ClientTurn)request.getSession().getAttribute("CurrentGame"); 
		gjd = (GameJoinData)request.getSession().getAttribute("GameJoined");
	%>
	
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/Chart.bundle.min.js"%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script type="text/javascript">
		var player;	//can be host or client
		<% 	if (ct != null) {
				ct.setType(ClientTurn.TYPE.HOST.toString());
				jo = new JSONObject(ct);
		
			} else if (gjd != null) {
				ClientTurn jct = new ClientTurn();
				jct.setGameId(gjd.getGameId());
				jct.setCurrentTurn(0);
				jct.setType(ClientTurn.TYPE.CLIENT.toString());
				jct.setPlayer(gjd.getPlayerName());
				jo = new JSONObject(jct);
			}
		%>
		var clientTurnJSON = <%= jo%>
		var serviceUrl = "<%= serviceUrl%>";
	</script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/gamepage_script.js"%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/chart-control.js"%>"></script>
</body>
</html>