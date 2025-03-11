<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
	<head>
		<title>Signup</title>
	</head>
	<body>
		<h1>Please Signup <br></h1>

		<!-- Form to check Customer ID -->
		<form action="/check_cid" method="GET">
			Enter Customer ID: 
			<input type="text" name="cid" value="<%= request.getAttribute("cidValue") != null ? request.getAttribute("cidValue") : "" %>">
			<input type="submit" value="Check ID">
		</form>

		<!-- Display messages -->
		<h3 style="color: red;"><%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %></h3>
		<h3 style="color: green;"><%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %></h3>
		<h3 style="color: red;"><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></h3>

		<!-- If CID is valid, show password input -->
		<% if (request.getAttribute("cidValid") != null) { %>
			<form action="/submit_signup" method="GET">
				<input type="hidden" name="cid" value="<%= request.getAttribute("cidValid") %>">
				Enter Name: 	<input type="text" name="cname" 
				               value="<%= request.getAttribute("nameValue") != null ? request.getAttribute("nameValue") : "" %>">
				        <br>
				Enter Password: <input type="password" name="pwd">
				<br>
				Confirm Password: <input type="password" name="pwdconfirm">
				<input type="submit" value="Signup">
			</form>
		<% } %>

		<h4>Already have a Customer ID? Please <a href="/login"> Login </a></h4>
	</body>
</html>
