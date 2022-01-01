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
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
		<nav class="navbar navbar-inverse">
		  <div class="container-fluid">
		    <div class="navbar-header">
		      <a class="navbar-brand" href="<%=str%>/connexion">FabricTout</a>
		    </div>
		    <ul class="nav navbar-nav navbar-right">
		    <% if(user != null && user.getSerialNumber()!=0){ %>
		      <li><a href="<%= str%>/logout"><span class="glyphicon glyphicon-log-off"></span>Logout</a></li>
		    <%} %>
		    </ul>
		  </div>
		</nav>
	</body>
</html>