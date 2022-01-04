<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.FactoryMachine" %>
<%@page import="be.project.javabeans.Order" %>
<%@page import="java.util.ArrayList" %>
<%@ include file="Navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>List of the orders</title>
</head>
<body>
	<% ArrayList<Order> orders=(ArrayList<Order>) request.getAttribute("orders"); %>
	<% if(orders!=null){
		for(int i=0;i<orders.size();i++){%>
			<p><%=orders.get(i).getId() %><p>
	<%}} %>
	<div class="container">
	</div>
</body>
</html>