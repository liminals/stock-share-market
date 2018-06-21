<%@page import="com.liminal.model.GameJoinData"%>
<%@page import="com.liminal.model.GameHostingData"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/rest/";
%>
<%! GameHostingData ghd;
	GameJoinData gjd;	
%>
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
	<div>
		<!-- initial visit by player -->
		<% if (request.getSession().getAttribute("HostedGame") == null && request.getSession().getAttribute("GameJoinData") == null) { %>
		<div>
			<h3>Host a game</h3>
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/HostGame" %>" method="post">
				<input type="text" name="turns" placeholder="Turns in the game">
				<input type="text" name="createdBy" placeholder="Your name, for testing only">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Host">
			</form>
		</div>
		<div>
			<h3>Join a game</h3>
			<div id="gamesInfoArea"></div>
			<br />
			<span id="selectedGame"></span>
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/JoinHostedGame" %>" method="post">
				<input type="text" id="joinGameId" name="gameId" placeholder="Enter game id from above">
				<input type="text" name="username" placeholder="Player Name">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Join" id="joinGame">
			</form>
		</div>
		
		<!-- players joins a hosted game -->
		<% } else if (request.getSession().getAttribute("GameJoinData") != null && request.getSession().getAttribute("HostedGame") == null) {%>
			<div id="gamesInfoArea"></div>
			<br />
			<% gjd = (GameJoinData) request.getSession().getAttribute("GameJoinData");%>
			<% if (gjd.getStatus_message().equalsIgnoreCase(GameJoinData.MESSAGE.PLAYER_WITH_SAME_NAME_EXISTS.toString())) { %>
				<h4>Please use another username</h4>
				<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/JoinHostedGame" %>" method="post">
					<input type="text" id="joinGameId" name="gameId" value="<%= gjd.getGameId()%>">
					<input type="text" name="username" placeholder="Player Name">
					<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
					<input type="submit" value="Join" id="joinGame">
				</form>
			<% } else { %>
				<h4>Please enter a valid game id</h4>
				<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/JoinHostedGame" %>" method="post">
					<input type="text" id="joinGameId" name="gameId" placeholder="Enter game id from above">
					<input type="text" name="username" value="<%= gjd.getPlayerName()%>">
					<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
					<input type="submit" value="Join" id="joinGame">
				</form>
			<% } %>
		<!-- player hosts a game -->
		<% } else { %> 
			<% ghd = (GameHostingData) request.getSession().getAttribute("HostedGame");%>
			
			<h3>Currently Hosting</h3>
			<div id="hostedGameInfoArea"></div>
			<script type="text/javascript">
				<% JSONObject ob = new JSONObject(ghd); %>
				var hostedGameInfo = <%= ob %>;
			</script>
			
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/StartHostedGame" %>" method="post">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Start">
			</form>
		<% } %>
	</div>	
	
	<script type="text/javascript">
		var serviceUrl = "<%= serviceUrl%>";
	</script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/indexpage_script.js"%>"></script>
	<% if (ghd != null) {%>
		<script type="text/javascript">
			loadHostedGameInfo(hostedGameInfo);
		</script>
	<%	} %>
</body>
</html>