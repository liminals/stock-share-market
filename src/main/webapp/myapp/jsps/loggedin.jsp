<%@page import="com.liminal.model.Player"%>
<%@page import="com.liminal.model.GameJoinData"%>
<%@page import="com.liminal.model.GameHostingData"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%! 
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
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
	<link rel="stylesheet" href="../styles/loggedin_page.css">
</head>
<body id="mainBG">
	<% if (p == null) {
		response.sendRedirect("./../../index.jsp");
	} else {
	%>
	<div class="container">
	<div class="row">
		<div class="col">
			<h1 align="center">Liminals</h1><h2 align="center">Stock-Share-Market Simulation Game</h2><h6 style="color:white;" align="right"><%= p.getUsername() %> <form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/LogoutPlayer" %>" method="post">
			<input type="hidden" value="<%= serviceUrl%>" name="serviceUrl">
			<input class="button btn-primary" type="submit" value="Logout">
		</form></h6>
		</div>
	</div>
	<div class="row">
		<div class="col">
			
		</div>
	</div>
	<div id="playerDetails" class="row">
		<div class="col">
			<h2> Welcome : <%= p.getUsername() %></h2>
		</div>
	</div>
		<div id="container">
			<!-- initial visit by player -->
			<% if (request.getSession().getAttribute("HostedGame") == null && request.getSession().getAttribute("GameJoinData") == null) { %>
			<div class="row">
				<div class="col">
					<h3>Host a game</h3>
					<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/HostGame" %>" method="post">
						<div class="form-group">
							<input type="text" class="form-control" name="turns" placeholder="More than 3 less than 40" id="turnsId">
						</div>
						<input type="hidden" name="createdBy" value="<%= p.getUsername() %>">
						<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
						<div class="form-group">
							<input type="submit" value="Host" class="form-control btn btn-primary" id="hostGame">
						</div>
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<h3>Join a game</h3>
					<span id="selectedGame"></span>
					<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/JoinHostedGame" %>" method="post">
						<div class="form-group">
							<input type="text" id="joinGameId" name="gameId" placeholder="Enter game id from below" class="form-control">
						</div>
						<input type="hidden" name="username" value="<%= p.getUsername() %>">
						<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
						<div class="form-group">
							<input type="submit" value="Join" id="joinGame" class="form-control btn btn-primary">
						</div>
					</form>
				</div>
			</div>
			<div styles="margin-top: 5px;">
				<div id="gamesInfoArea"></div>
			</div>
			<!-- players joins a hosted game -->
			<% } else if (request.getSession().getAttribute("GameJoinData") != null && request.getSession().getAttribute("HostedGame") == null) {%>
				<% gjd = (GameJoinData) request.getSession().getAttribute("GameJoinData");%>
				<% if (gjd.getStatus_message().equalsIgnoreCase(GameJoinData.MESSAGE.GAME_NOT_AVAILABLE.toString())) { %>
					<h4>Please enter a valid game id</h4>
					<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/JoinHostedGame" %>" method="post">
						<div class="fomr-group">
							<input type="text" id="joinGameId" name="gameId" placeholder="Enter game id from below" class="form-control btn btn-primary">
						</div>
						<input type="hidden" name="username" value="<%= p.getUsername() %>">
						<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
						<div class="fomr-group">
							<input type="submit" value="Join" id="joinGame" class="form-control btn btn-primary">
						</div>
					</form>
					<br />
					<div id="gamesInfoArea"></div>
				<% } %>
			<% } else if (request.getSession().getAttribute("GameJoinData") == null && request.getSession().getAttribute("HostedGame") != null) {
					response.sendRedirect(request.getContextPath() + "/myapp/jsps/HostGame.jsp");
			   } 
			%> 
	</div>	
		
		
		</div>
		<!-- Game scripts -->
		
		<script type="text/javascript">
			var serviceUrl = "<%= serviceUrl%>";
		</script>
		<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
		<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/logged_script.js"%>"></script>
	<% } %>
</body>
</html>