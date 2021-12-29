<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.FactoryMachine" %>
<%@page import="be.project.javabeans.Maintenance" %>
<%@page import="be.project.javabeans.Worker" %>
<jsp:useBean id="connectedUser" class="be.project.javabeans.Leader" scope="session"></jsp:useBean>
<jsp:useBean id="machine" class="be.project.javabeans.FactoryMachine" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Create a maintenance</title>
</head>
<body>
	<%@ include file="Navbar.jsp" %>
	<div class="container">
		<h3 class="text-center">Create a new maintenance for the machine <%=machine.getId() %> : <%=machine.getModel() %></h3>
		<form method="POST" action="CreateMaintenance">
			<div class="mb-3">
				<label for="date" class="form-label">Date : </label>
				<input type="date" class="form-control" id="date" name="date">
			</div>
			<div class="mb-3">
				<h6  class="form-label">Select the worker(s) for the maintenance :</h6>
				<div class="form-check">
					
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>