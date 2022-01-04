<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
<%@page import="be.project.javabeans.Worker" %>
<%@page import="be.project.javabeans.Leader" %>
<%@page import="be.project.javabeans.Employee" %>
<%@page import="be.project.javabeans.User" %>
    <jsp:useBean id="worker" class="be.project.javabeans.Worker" scope="session"></jsp:useBean>
    <jsp:useBean id="leader" class="be.project.javabeans.Leader" scope="session"></jsp:useBean>
    <jsp:useBean id="employee" class="be.project.javabeans.Employee" scope="session"></jsp:useBean>
  	
<!DOCTYPE html>
<html>
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<meta charset="ISO-8859-1">
		<title>FabricTout</title>
	</head>
	<body>
		<%
		int URLLength = (int)request.getRequestURL().length();
		int servPathLength = (int)request.getServletPath().length();
		String chaine = request.getRequestURL() + request.getServletPath();
		String str = chaine.substring(0, URLLength-servPathLength);
		%>
		<% 
			User user = (User)session.getAttribute("connectedUser");
		%>
		
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="<%=str%>/connexion">FabricTout</a>
		    
		    <div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
		      <ul class="navbar-nav justify-content-center">
		    	<% if(user != null && user.getSerialNumber()!=0 && (user instanceof Employee)){ %>
		      		<li class="nav-item"><a class="nav-link active" href="orders"><span class="glyphicon"></span>Orders</a></li>
		      		<li class="nav-item"><a class="nav-link active" href="maintenances"><span class="glyphicon"></span>Maintenances</a></li>
		   		<%} %>
		       <% if(user != null && user.getSerialNumber()!=0){ %>
		      		<li class="nav-item"><a class="nav-link active" href="<%= str%>/logout"><span class="glyphicon glyphicon-log-off"></span>Logout</a></li>
		    	<%} %>
		      </ul>
		      
		    </div>
		  </div>
		</nav>
	</body>
</html>