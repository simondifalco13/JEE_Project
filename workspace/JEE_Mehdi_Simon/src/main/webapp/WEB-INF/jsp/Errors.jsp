<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
   <%@ include file="Navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Page d'erreurs</title>
</head>
	<body>
		<div class="alert alert-danger" role="alert">Une exception est survenue</div>

		<p><%= exception%></p>
		<p><%= exception.getMessage() %></p>
	</body>
</html>