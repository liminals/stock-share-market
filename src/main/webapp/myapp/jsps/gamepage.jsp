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
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
</head>
<body>
	<%
		String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	%>
	<div id="container">
		<h1>Game Page</h1>
		<h3 id="playerName"></h3>
		<h3 id="currentBalance"></h3>
		<h3 id="currentTurn">Current Turn:</h3>
		<h3 id="totalTurns"> Total Turns :</h3>
		<h4>Stocks</h4>
		
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
		
		<div id="stockTransaction">
			<select id="stocks"></select>
			<div id="stockDetails">
				<h4 id="stockname"></h4>
				<p id="stockprice">
			</div>
			<div id="transactionStocksDiv">
				 <div id="transactionButtons">
					<button id="buttonBuy">Buy</button>
					<button id="buttonSell">Sell</button>
				 </div>
				 <div id="transactionFields">
					<label>Value : </label>
					<input type="text" id="transactionValue" disabled="true">
					<input type="text" id="transactionQty">
				 </div>
				 <div id="latestTransaction"></div>
			</div>
		</div>
	</div>
	
	
	
	

	
	
	
	
	
	<% 	ct = (ClientTurn)request.getSession().getAttribute("CurrentGame"); 
		gjd = (GameJoinData)request.getSession().getAttribute("GameJoined");
	%>
	
	
	<!-- Game scripts -->
		
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/Chart.bundle.min.js"%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>

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
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/transactions-control.js"%>"></script>
</body>
</html>