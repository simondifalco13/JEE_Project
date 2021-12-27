<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.FactoryMachine" %>
<jsp:useBean id="machine" class="be.project.javabeans.FactoryMachine" scope="request"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Manage Machine</title>
</head>
<body>
	<%@ include file="Navbar.jsp" %>
	<div class="container">
		<h2>Hello manage your machine </h2>
		<p><%=machine.getId() %> <%=machine.getBrand() %></p>
	</div>
</body>
</html>