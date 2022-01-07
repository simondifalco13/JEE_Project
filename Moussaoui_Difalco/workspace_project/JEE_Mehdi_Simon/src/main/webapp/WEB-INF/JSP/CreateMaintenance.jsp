<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Errors.jsp"%>
<%@page import="be.project.javabeans.FactoryMachine" %>
<%@page import="be.project.javabeans.Maintenance" %>
<%@page import="be.project.javabeans.Worker" %>
<%@page import="be.project.javabeans.Area" %>
<%@page import="be.project.javabeans.Site" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.lang.String" %>
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
		<% ArrayList<Worker> siteWorkers=new ArrayList<Worker>(); 
			ArrayList<Area> machineAreas=machine.getMachineAreas();
			for(int i=0;i<machineAreas.size();i++){
				Site areaSite=machineAreas.get(i).getAreaSite();
				siteWorkers=areaSite.getSiteWorkers();
			}
		%>
		
		<div class="container">
			<% if(request.getAttribute("error")!=null){%>
				<div class="alert alert-danger" role="alert">
	  				<%= request.getAttribute("error") %>
				</div>
			<% } %>
			<h3 class="text-center">Create a new maintenance for the machine <%=machine.getId() %> : <%=machine.getModel() %></h3>
			<form method="POST" action="CreateMaintenance">
				<div class="mb-3 m-2">
					<label for="date" class="form-label">Date : </label>
					<input type="date" class="form-control" id="date" name="date">
				</div>
				<div class="mb-3">
					<label for="start" class="form-label">Select a time to start the maintenance:</label>
					<input type="time" id="start" name="start" class="form-control">
				</div>
				<div class="mb-3">
					<h5 class="form-label">Select the worker(s) for the maintenance :</h5>
					<div class="form-check ">
						<%for(int j=0;j<siteWorkers.size();j++){%>
							<div class="form-check">
							  <input class="form-check-input" type="checkbox" value="<%=siteWorkers.get(j).getSerialNumber() %>" id="check" name="worker">
							  <label class="form-check-label" for="check">
							    <%=siteWorkers.get(j).getFirstname() %>  <%=siteWorkers.get(j).getLastname() %> 
							  </label>
							</div>
						<%}%>
					</div>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto m-2">
					  <button type="submit" class="btn btn-primary">Validate</button>
				</div>
				<div class="d-grid gap-2 col-6 mx-auto m-2">
					  <a href="machines" class="btn btn-primary">Cancel</a>
				</div>
			</form>
		</div>
	</body>
</html>