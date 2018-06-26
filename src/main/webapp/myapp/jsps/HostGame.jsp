<%@page import="org.json.JSONObject"%>
<%@page import="com.liminal.model.GameHostingData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Hosting Game</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
	<link rel="stylesheet" href="../styles/hostgame_page.css">
</head>
<body>
	<!-- player hosts a game -->
	<%!GameHostingData ghd; %>
	<% 
		String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/rest/";
		HttpSession s = request.getSession();
	if (s.getAttribute("CurrentPlayer") != null && s.getAttribute("HostedGame") != null && s.getAttribute("CurrentGame") == null) { %>
			<% ghd = (GameHostingData) s.getAttribute("HostedGame");%>
			<div class="container">
				<div class="row justify-content-center">
					<h2>Currently Hosting</h3>
				</div>
				<div class="row">
					<div class="col">
						<div id="hostedGameInfoArea">
							<div id="gameInfo"></div>
							<div id="joinedPlayers"></div>
						</div>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col">
						<form action="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/StartHostedGame"%>" method="post">
							<input type="hidden" name="serviceUrl" value=<%=serviceUrl%>>
							<div class="form-group">
								<input type="submit" value="Start" class="form-control btn btn-success">
							</div>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<form action="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/StopHostedGame"%>" method="post">
							<input type="hidden" name="serviceUrl" value=<%=serviceUrl%>>
							<div class="form-group">
								<input type="submit" value="Stop" class="form-control btn btn-danger">
							</div>
						</form>
					</div>
				</div>
			</div>
	<%	// game host access this page
		} else if (s.getAttribute("CurrentPlayer") != null && s.getAttribute("HostedGame") != null && s.getAttribute("CurrentGame") != null) {
			response.sendRedirect(request.getContextPath() + "/myapp/jsps/gamepage.jsp");
		// player access this page
		} else if (s.getAttribute("CurrentPlayer") != null && s.getAttribute("HostedGame") == null) {
			response.sendRedirect(request.getContextPath() + "/myapp/jsps/loggedin.jsp");
		// joined client access this page while in game
		} else if(s.getAttribute("CurrentPlayer") != null && s.getAttribute("GameJoinData") != null) {
			response.sendRedirect(request.getContextPath() + "/myapp/jsps/gamepage.jsp");
		// unsigned player
		} else {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	%>
	<!-- Game scripts -->
		<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
		<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/host_game.js"%>"></script>
		<script type="text/javascript">
			var serviceUrl = "<%= serviceUrl%>";
			<%JSONObject ob = new JSONObject(ghd);%>
			var hostedGameInfo =<%=ob%>;
			loadHostedGameInfo(hostedGameInfo);
		</script>
</body>
</html>