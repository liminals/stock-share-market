<%@page import="com.liminal.model.Player"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/rest/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Liminal Stock-Share-Market</title>
	<link rel="stylesheet" href="./myapp/styles/main.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
</head>
<body>
	<% 	HttpSession sess = request.getSession();
		Player p = (Player) sess.getAttribute("CurrentPlayer");
	%>
	<h1>Liminals</h1>
	<h2>Welcome user!</h2>
	<div id="container">
	
	<% if (p == null) { %>
	<!-- initial visit && register success -->
	<% if (request.getAttribute("RegisterErrorMessage") == null && request.getAttribute("LoginErrorMessage") == null) {%>
		<% if (request.getAttribute("Registration") != null) { %>
			<span id="successMessage"><%= request.getAttribute("Registration") %></span>
		<% 
			request.removeAttribute("RegisterErrorMessage");
		} %>
		<div id="createAccount">
			<h2 id="headings">Create Account</h2>
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/RegisterPlayer" %>" method="post">
				<input type="text" name="username" placeholder="Username">
				<input type="password" name="password" placeholder="Password">
				<input type="password" name="verify">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Register">
			</form>
		</div>
		<br/>
		<div id="loginAccount">
		<h2 id="headings">Login Account</h2>
			<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/LoginPlayer" %>" method="post">
				<input type="text" name="username" placeholder="Username">
				<input type="password" name="password" placeholder="Password">
				<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
				<input type="submit" value="Login">
			</form>
		</div>
		
		<!-- create account failed  -->
		<% } else if (request.getAttribute("RegisterErrorMessage") != null && request.getAttribute("LoginErrorMessage") == null){%>
			<div id="createAccount">
				<h2 id="headings">Create Account</h2>
				<span id="errorMessage"><%= request.getAttribute("RegisterErrorMessage") %></span>
				<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/RegisterPlayer" %>" method="post">
					<input type="text" name="username" placeholder="Username">
					<input type="password" name="password" placeholder="Password">
					<input type="password" name="verify">
					<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
					<input type="submit" value="Register">
				</form>
			</div>
			
		<!-- login failed -->		
		<% } else if (request.getAttribute("RegisterErrorMessage") == null && request.getAttribute("LoginErrorMessage") != null){%>
			<div id="loginAccount">
				<h2 id="headings">Login Account</h2>
				<span id="errorMessage"><%= request.getAttribute("LoginErrorMessage") %></span>
				<form action="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/LoginPlayer" %>" method="post">
				<% if(request.getAttribute("LoginErrorMessage").toString().equalsIgnoreCase("Invalid Password")) { %>
					<input type="text" name="username" value="<%= request.getAttribute("Username")%>">
				<% } else {%>
					<input type="text" name="username" placeholder="Username">
				<% } %>
					<input type="password" name="password" placeholder="Password">
					<input type="hidden" name="serviceUrl" value=<%= serviceUrl%>>
					<input type="submit" value="Login">
				</form>
			</div>	
		<% } %>
	</div>
	<%  } else {
			response.sendRedirect(request.getContextPath() + "/myapp/jsps/loggedin.jsp");
		}
	%>
	
	<!-- Game scripts -->
	
	<script type="text/javascript" src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
</body>
</html>