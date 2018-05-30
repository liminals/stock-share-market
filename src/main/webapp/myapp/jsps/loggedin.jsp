<%@page import="com.liminal.model.Player"%>
<%@page import="com.liminal.model.GameJoinData"%>
<%@page import="com.liminal.model.GameHostingData"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%! GameHostingData ghd;
	GameJoinData gjd;
	Player p;
%>    
<%
	String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/rest/";
	p = (Player) request.getSession().getAttribute("CurrentPlayer");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Stock-Share-Market</title>
	<link rel="stylesheet" href="../styles/loggedin_page.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
</head>
<body id="mainBG">
	<h1>Liminals</h1>
	<h2>Stock-Share-Market Simulation Game</h2>
	
	<div id="playerDetails">
		<h2> Welcome : <%= p.getUsername() %></h2>
	</div>
	<div id="container">
		<!-- initial visit by player -->
		<% if (request.getSession().getAttribute("HostedGame") == null && request.getSession().getAttribute("GameJoinData") == null) { %>
		<div>
			<h3>Host a game</h3>
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/HostGame" %>" method="post">
				<input type="text" name="turns" placeholder="Fixed turns in the game">
				<input type="hidden" name="createdBy" value="<%= p.getUsername() %>">
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
				<input type="hidden" name="username" value="<%= p.getUsername() %>">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Join" id="joinGame">
			</form>
		</div>
		
		<!-- players joins a hosted game -->
		<% } else if (request.getSession().getAttribute("GameJoinData") != null && request.getSession().getAttribute("HostedGame") == null) {%>
			<div id="gamesInfoArea"></div>
			<br />
			<% gjd = (GameJoinData) request.getSession().getAttribute("GameJoinData");%>
			<% if (gjd.getStatus_message().equalsIgnoreCase(GameJoinData.MESSAGE.GAME_NOT_AVAILABLE.toString())) { %>
				<h4>Please enter a valid game id</h4>
				<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/JoinHostedGame" %>" method="post">
					<input type="text" id="joinGameId" name="gameId" placeholder="Enter game id from above">
					<input type="hidden" name="username" value="<%= p.getUsername() %>">
					<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
					<input type="submit" value="Join" id="joinGame">
				</form>
			<% } %>
		<!-- player hosts a game -->
		<% } else { %> 
			<% ghd = (GameHostingData) request.getSession().getAttribute("HostedGame");%>
			
			<h3>Currently Hosting</h3>
			<div id="hostedGameInfoArea">
				<div id="gameInfo"></div>
				<div id="joinedPlayers"></div>
			</div>
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
	
	
	
	<!-- Game scripts -->
	
	<script type="text/javascript">
		var serviceUrl = "<%= serviceUrl%>";
	</script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/logged_script.js"%>"></script>
	<% if (ghd != null) {%>
		<script type="text/javascript">
			loadHostedGameInfo(hostedGameInfo);
		</script>
	<%	} %>
</body>
</html>