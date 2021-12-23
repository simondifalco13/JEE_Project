<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
<%@page import="be.project.javabeans.Worker" %>
<%@page import="be.project.javabeans.Leader" %>
<%@page import="be.project.javabeans.Employee" %>
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
<% worker = (Worker)session.getAttribute("worker"); 
   leader = (Leader)session.getAttribute("leader");
   employee = (Employee)session.getAttribute("employee");
%>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">FabricTout</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
    <% if(leader == null && worker == null && employee == null){%>      
      <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>
    <% } %>
    <% if(worker != null || leader != null || employee != null){ %>
      <li><a href="#"><span class="glyphicon glyphicon-log-off"></span> Se déconnecter</a></li>
    <%} %>
    </ul>
  </div>
</nav>
</body>
</html>