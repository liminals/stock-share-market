<%@page import="com.liminal.model.GameHostingData"%>
<%@page import="com.liminal.model.Player"%>
<%@page import="com.liminal.model.GameJoinData"%>
<%@page import="com.liminal.controller.JoinHostedGame"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.liminal.model.ClientTurn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%! 
	ClientTurn ct; 
	GameHostingData ghd;
	GameJoinData gjd;
	JSONObject jo;
	Player p;
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Game Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
	<link rel="stylesheet" href="../styles/gamepage.css">
</head>
<body>
	<%	
		HttpSession sess = request.getSession();
		// for logged in player
		p = (Player) sess.getAttribute("CurrentPlayer");
		// if host: only host has a ClientTurn session object
		ghd = (GameHostingData) sess.getAttribute("HostedGame");
		ct = (ClientTurn) sess.getAttribute("CurrentGame"); 
		// if client
		gjd = (GameJoinData) sess.getAttribute("GameJoined");
		String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	%>
			<!-- this is for host                   || this is for players who join -->
	<% if ((p != null && ghd != null && ct != null) || (p != null && gjd != null)) { %>
	
	<div class="container">
		<div id="container">
		<div class="row">
		<div class="col-md-12">
			<h1 align="center">Game Page</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
			<center>
				<h4 align="center" id="playerName"></h4>
				<h4 align="center" id="currentBalance"></h4>
				<h4 id="currentTurn">Current Turn:</h4>
				<h4 id="totalTurns"> Total Turns :</h4>
				<h4 id="currentWinner"></h4>
				<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/FinishGame"%>" method="post">
					<input type="hidden" value=<%= serviceUrl%> name="serviceUrl">
					<input class="button btn" type="submit" value="Finish" disabled="true" id="leaveGame">
				</form>
				</center>
			</div>
			
		</div>
		
		<br>
		<div class="row">
			<div class="col">
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
			</div>
		</div>
		<br>
			<div id="stockTransaction">
			<div class="row">
				<div class="col">
					<div id="stockBuy">
					<div class="form-group">
						Buy : <select class="form-control" id="selectBuy"></select>
					</div>
					<div class="form-group">
						<button id="buttonBuy" class="form-control btn btn-primary">Buy</button>
					</div>
						<div id="buyStockDetails">
							<h4 id="stockname"></h4>
							Price : <p id="stockprice">
						</div>
						<div id="buyTransactionFields">
							<div class="form-group">
								<label class="form-label">Value : </label>
							</div> 
							<div class="form-group">
								<input type="text" id="transactionValue" disabled="true" class="form-control">
							</div> 
							<div class="form-group"> 
								<input type="text" id="transactionQty" class="form-control">
							</div> 
						</div>
					</div>
				</div>
			</div>
				<br/>
			<div class="row">
				<div class="col">
					<div id="stockSell">
					<div class="form-group">
						Sell : <select id="selectSell" class="form-control"></select>
					</div>
					<div class="form-group">
						<button id="buttonSell" class="form-control btn btn-warning">Sell</button>
					</div>
						<div id="sellStockDetails">
							<h4 id="portfolioname"></h4>
							Qty : <p id="portfolioqty">
							Value : <p id="portfoliovalue">
						</div>
						<div id="sellTransactionFields">
							<label>Sell Qty : </label>
							<div class="form-group">  
								<input type="text" id="sellQty" class="fomr-control">
							</div>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col">
					<span id="latestTransaction"></span>
				</div>
			</div>
			</div>
			<br/>
			<div class="row">
				<div class="col">
					<div id="portfolioDiv"></div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col">
					<div id="transactionHistory"></div>
				</div>
			</div>
		</div>
	</div>



	<!-- when a game host access this url without starting the game -->
	<% 	} else if (p != null && ghd != null && ct == null)  {
			response.sendRedirect(request.getContextPath() + "/myapp/jsps/HostGame.jsp");
			// if player only logged in
		} else if (p != null) {
			response.sendRedirect(request.getContextPath() + "/myapp/jsps/loggedin.jsp");
			// if player not logged in
		} else {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
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
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/transaction-functions.js"%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/gamepage_script.js"%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/chart-control.js"%>"></script>
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/transactions-control.js"%>"></script>
</body>
</html>