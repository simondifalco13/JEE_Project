<%@page import="javax.websocket.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="Errors.jsp" session="true"
	import="be.project.javabeans.Worker"%>
	<%@page import="be.project.javabeans.Maintenance"  %>
	<%@page import="be.project.enumerations.MaintenanceStatus"  %>
	<%@ include file="Navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta charset="ISO-8859-1">
<title>Page d'accueil</title>
</head>
<body>
	<% worker = (Worker)session.getAttribute("worker"); %>
	<h2>Bienvenue cher/chère <%= worker.getFirstname() %> </h2>
	
	<table class="table">
  <thead class="thead-dark">
    <tr>
      <th>Maintenance en cours</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
   
    <% for(Maintenance maintenance : worker.getMaintenances()){ 
    	if(maintenance.getStatus() == MaintenanceStatus.ongoing 
    	   || maintenance.getStatus() == MaintenanceStatus.toDo 
    	   || maintenance.getStatus() == MaintenanceStatus.toRedo){			
   	%>
   	 <tr>
      <td>Leadeur de maintenance : <%= maintenance.getMaintenanceLeader().getFirstname()%></td>
      <td>Status <%= maintenance.getStatus()%></td>
    </tr>
    <%}} %>
  </tbody>
</table>
</body>
</html>