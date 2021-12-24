<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.Leader" %>
<%@page import="be.project.javabeans.FactoryMachine" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="connectedUser" class="be.project.javabeans.Leader" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Machines page</title>
</head>
<body>
	<% ArrayList<FactoryMachine> machines=(ArrayList<FactoryMachine>)request.getAttribute("machines"); %>
	<%@ include file="Navbar.jsp" %>
	<div class="container">
		<h4>Welcome on the machines pages</h4>
		<div>
			<% for(int i=0;i<machines.size();i++){
					%>
					<div class="card" style="width: 18rem;">
					  <div class="card-body">
					    <h5 class="card-title"><%= machines.get(i).getModel() %></h5>
					    <p class="card-text">Brand : <%=machines.get(i).getBrand() %></p>
					    <a href="#" class="btn btn-primary">Go somewhere</a>
					  </div>
					</div>
					<%
				}
			%>
			
		</div>
	</div>
</body>
</html>