<%@page import="com.liminal.model.Player"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String serviceUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/rest/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Liminal Stock-Share-Market</title>

<link rel="stylesheet" href="./myapp/styles/main.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css"
	integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy"
	crossorigin="anonymous">
</head>
<body>


	<div class="container" id="container">

		<!-- initial visit && register success -->
		<%
			if (request.getAttribute("RegisterErrorMessage") == null
					&& request.getAttribute("LoginErrorMessage") == null) {
		%>
		<%
			if (request.getAttribute("Registration") != null) {
		%>
		<span id="successMessage"><%=request.getAttribute("Registration")%></span>
		<%
			}
		%>
		<div class="row">
			<div class="col-md-4">
				<div class="card" style="background-color: #ffffff14; padding-top: 450px;">
				<center><h1 style="color: white;">WELCOME</h1></center>
					<div class="card-body">
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<center>
					<div class="card"
						style="background-color: #ffffff14; padding-top: 100px;">
						<div id="loginAccount">
							<h2 style="color: white;" id="headings">
								Hi !<br /> Welcome To Liminals!<br /> Login Account
							</h2>
						</div>

						<div class="card-body">
							<form
								action="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
						+ "/LoginPlayer"%>"
								method="post">
								<div class="input-group">
									<input class="form-control" type="text" name="username"
										placeholder="Username" style="background-color: #ffffff21;">
								</div>
								<div class="input=group">
									<input class="form-control" type="password" name="password"
										placeholder="Password" style="background-color: #ffffff21;">
								</div>
								<div class="input=group">
									<input class="form-control" type="hidden" name="serviceUrl"
										value=<%=serviceUrl%> style="background-color: #ffffff21;">
								</div>
								<div class="input=group">
									<input class="form-control" type="submit" value="Login"
										style="background-color: #ffffff21;">
								</div>
							</form>
						</div>
						<div>
				</center>
			</div>
			<div class="col-md-4">
				<div class="card"
					style="background-color: #ffffff21; padding-top: 300px;">
					<div id="createAccount" class="panel-heading">
						<center>
							<h2 id="headings" style="color: white;">Create Account</h2>
						</center>
					</div>
					<div class="card-body">
						<center>
							<form
								action="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
						+ "/RegisterPlayer"%>"
								method="post">
								<div class="input-group">
									<input class="form-control" type="text" name="username"
										placeholder="Username" style="background-color: #0c0909c9;">
								</div>
								<div class="input-group">
									<input class="form-control" type="password" name="password"
										placeholder="Password" style="background-color: #0c0909c9;">
								</div>
								<div class="input-group">
									<input class="form-control" type="password"
										placeholder="confirm password" name="verify"
										style="background-color: #0c0909c9;">
								</div>
								<div class="input-group">
									<input class="form-control" type="hidden" name="serviceUrl"
										value=<%=serviceUrl%>>
								</div>
								<div class="input-group">
									<input class="form-control" type="submit" value="Register"
										style="background-color: #0c0909c9;">
								</div>
							</form>
						</center>
					</div>
				</div>
			</div>
		</div>



		<!-- create account failed  -->
		<%
			} else if (request.getAttribute("RegisterErrorMessage") != null
					&& request.getAttribute("LoginErrorMessage") == null) {
		%>
		<div id="createAccount">
			<h2 id="headings">Create Account</h2>
			<span id="errorMessage"><%=request.getAttribute("RegisterErrorMessage")%></span>
			<form
				action="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
						+ "/RegisterPlayer"%>"
				method="post">
				<input type="text" name="username" placeholder="Username"> <input
					type="password" name="password" placeholder="Password"> <input
					type="password" name="verify"> <input type="hidden"
					name="serviceUrl" value=<%=serviceUrl%>> <input
					type="submit" value="Register">
			</form>
		</div>
	</div>
	<!-- login failed -->
	<%
		} else if (request.getAttribute("RegisterErrorMessage") == null
				&& request.getAttribute("LoginErrorMessage") != null) {
	%>
	<div id="loginAccount">
		<h2 id="headings">Login Account</h2>
		<span id="errorMessage"><%=request.getAttribute("LoginErrorMessage")%></span>
		<form
			action="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
						+ "/LoginPlayer"%>"
			method="post">
			<%
				if (request.getAttribute("LoginErrorMessage").toString().equalsIgnoreCase("Invalid Password")) {
			%>
			<input type="text" name="username"
				value="<%=request.getAttribute("Username")%>">
			<%
				} else {
			%>
			<input type="text" name="username" placeholder="Username">
			<%
				}
			%>
			<input type="password" name="password" placeholder="Password">
			<input type="hidden" name="serviceUrl" value=<%=serviceUrl%>>
			<input type="submit" value="Login">
		</form>
	</div>
	<%
		}
	%>


	<!-- Game scripts -->

	<script type="text/javascript"
		src="<%=request.getContextPath() + "/myapp/js/jquery-3.3.1.min.js"%>"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js"
		integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4"
		crossorigin="anonymous"></script>
</body>
</html>