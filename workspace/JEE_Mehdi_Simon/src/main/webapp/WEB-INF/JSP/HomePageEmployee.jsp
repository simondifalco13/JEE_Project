<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.Leader" %>
<%@page import="be.project.javabeans.FactoryMachine" %>
<%@page import="be.project.javabeans.Area" %>
<%@page import="be.project.javabeans.Maintenance" %>
<%@page import="be.project.javabeans.Leader" %>
<%@page import="be.project.enumerations.OperationState" %>
<%@page import="be.project.enumerations.MaintenanceStatus" %>
<%@page import="be.project.javabeans.Worker" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<jsp:useBean id="connectedUser" class="be.project.javabeans.Employee" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Maintenance history</title>
</head>
<body>
	<% ArrayList<FactoryMachine> machines=(ArrayList<FactoryMachine>)request.getAttribute("machines");
	
	%>
	<%@ include file="Navbar.jsp" %>
	<div class="container">
	</div>
</body>
</html>