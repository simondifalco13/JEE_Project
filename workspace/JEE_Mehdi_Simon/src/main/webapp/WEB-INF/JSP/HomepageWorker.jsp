<%@page import="javax.websocket.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="Errors.jsp" session="true"
	import="be.project.javabeans.User"%>
	<%@page import="be.project.javabeans.Maintenance"  %>
	<%@page import="be.project.javabeans.Report"  %>
	<%@page import="be.project.enumerations.MaintenanceStatus"  %>
	<%@page import="java.text.DateFormat"%>
    <%@page import="java.text.SimpleDateFormat" %>
	<%@ include file="Navbar.jsp" %>
	
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Home page worker</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
	<body>
			<% 
				if(user instanceof Worker) {
					worker= (Worker)request.getAttribute("worker");
			%>
			<h2 style="text-align:center" >Welcome dear <%= user.getFirstname() %> </h2>
			<div class="centered">
				<table class="table table-bordered" style="width: auto">
				<caption>Ongoing maintenances</caption>
				 		<thead>
		                     <tr>
		                     	<th>Maintenance number</th>
		                        <th>Maintenance date</th>
		                        <th>Maintenance status</th>
		                        <th>Machine id</th>
		                        <th>Leader's name</th> 
		                        <th>Report submitted</th>
		                     </tr>
				 		</thead>
				 		<tbody>
				 		<% for(Maintenance maintenance : worker.getMaintenances()){ 
						    	if(maintenance.getStatus() == MaintenanceStatus.ongoing ){			
		   				%>
						   	 <tr>
							     <td><%= maintenance.getMaintenanceId()%></td>
							     <% SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
							     String dateAsString = maintenance.getMaintenanceDate().toString();
							     String date = DateFor.format(maintenance.getMaintenanceDate());%>
							     <td><%= date%></td>
							  	<% String status =maintenance.getStatus().toString().toUpperCase();%>
							     <td><%= status%></td>
							     <td><%= maintenance.getMachine().getId()%></td>
							     <td><%= maintenance.getMaintenanceLeader().getLastname()%></td>
						     	<% for(Report report : maintenance.getMaintenanceReports()){
						     			if(report.getReport()!=null){
						     				if(!report.getReport().isEmpty() && report.getWorker().getSerialNumber() == worker.getSerialNumber()){ 
						     	%>
						    	<td>Yes</td>
						    	 <% }}
						    	else {%>
						    	<td>No</td>
						    	<%}} %>
						    	<td><a class="btn btn-info" href="<%=str%>/maintenanceinfos?id=<%=maintenance.getMaintenanceId() %>" role="button">Consult</a>
						    	</td>
						    </tr>
						    <%}} %>
				 		</tbody>
				</table>
			</div>
			
			
			
			<div class="centered">
				<table class="table table-bordered" style="width: auto" >
				<caption>Todo/Toredo maintenances</caption>
				 		<thead class="thead-dark">
		                     <tr>
		                     	<th>Maintenance number</th>
		                        <th>Maintenance date</th>
		                        <th>Maintenance status</th>
		                        <th>Machine id</th>
		                        <th>Leader's name</th> 
		                        <th>Report submitted</th>
		                     </tr>
				 		</thead>
				 		<tbody>
				 		<% for(Maintenance maintenance : worker.getMaintenances()){ 
						    	if(maintenance.getStatus() == MaintenanceStatus.todo || maintenance.getStatus() == MaintenanceStatus.toredo ){			
		   				%>
						   	 <tr>
							     <td><%= maintenance.getMaintenanceId()%></td>
							     <% SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
							     String dateAsString = maintenance.getMaintenanceDate().toString();
							     String date = DateFor.format(maintenance.getMaintenanceDate());%>
							     <td><%= date%></td>
							     <% String status = maintenance.getStatus().toString().toUpperCase() ;%>
							     <td><%= status%></td>
							     <td><%= maintenance.getMachine().getId()%></td>
							     <td><%= maintenance.getMaintenanceLeader().getLastname()%></td>
						     	<% for(Report report : maintenance.getMaintenanceReports()){
						     			if(report.getReport()!=null){
						     				if(!report.getReport().isEmpty() && report.getWorker().getSerialNumber() == worker.getSerialNumber()){
						     	%>
						    	<td>Yes</td>
						    	 <% }}
						    	else {%>
						    	<td>No</td>
						    	<%}} %>
						    	<td><a class="btn btn-info" href="<%=str%>/maintenanceinfos?id=<%=maintenance.getMaintenanceId() %>" role="button">Consult</a>
						    	</td>
						    </tr>
						    <%}} %>
				 		</tbody>
				</table> 
			</div> 
			<div class="centered">
				<table class="table table-bordered" style="width: auto" >
				<caption>Maintenances waiting for validation</caption>
				 		<thead class="thead-dark">
		                     <tr>
		                     	<th>Maintenance number</th>
		                        <th>Maintenance date</th>
		                        <th>Maintenance status</th>
		                        <th>Machine id</th>
		                        <th>Leader's name</th> 
		                        <th>Report submitted</th>
		                     </tr>
				 		</thead>
				 		<tbody>
				 		<% for(Maintenance maintenance : worker.getMaintenances()){ 
						    	if(maintenance.getStatus() == MaintenanceStatus.done){			
		   				%>
						   	 <tr>
							     <td><%= maintenance.getMaintenanceId()%></td>
							     <% SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
							     String dateAsString = maintenance.getMaintenanceDate().toString();
							     String date = DateFor.format(maintenance.getMaintenanceDate());%>
							     <td><%= date%></td>
							     <%String status = maintenance.getStatus().toString().toUpperCase();%>
							     <td><%= status %></td>
							     <td><%= maintenance.getMachine().getId()%></td>
							     <td><%= maintenance.getMaintenanceLeader().getLastname()%></td>
						     	<% for(Report report : maintenance.getMaintenanceReports()){
						     			if(report.getReport()!=null){
						     				if(!report.getReport().isEmpty() && report.getWorker().getSerialNumber() == worker.getSerialNumber()){
						     	%>
						    	 <td>Yes</td><% }}
						    	else {%>
						    	<td>No</td>
						    	<%}} %>
						    	<td><a class="btn btn-info" href="<%=str%>/maintenanceinfos?id=<%=maintenance.getMaintenanceId() %>" role="button">Consult</a></td>
						    </tr>
						    <%}} %>
				 		</tbody>
				</table> 
			</div>
			<div class="centered">
				<table class="table table-bordered"  style="width: auto" >
					<caption>Validated maintenances</caption>
				 		<thead class="thead-dark">
		                     <tr>
		                     	<th>Maintenance number</th>
		                        <th>Maintenance date</th>
		                        <th>Maintenance status</th>
		                        <th>Machine id</th>
		                        <th>Leader's name</th> 
		                        <th>Report submitted</th>
		                     </tr>
				 		</thead>
				 		<tbody>
				 		<% for(Maintenance maintenance : worker.getMaintenances()){ 
						    	if(maintenance.getStatus() == MaintenanceStatus.validated){			
		   				%>
						   	 <tr>
							     <td><%= maintenance.getMaintenanceId()%></td>
							     <% SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
							     String dateAsString = maintenance.getMaintenanceDate().toString();
							     String date = DateFor.format(maintenance.getMaintenanceDate());%>
							     <td><%= date%></td>
							     <% String status =maintenance.getStatus().toString().toUpperCase(); %>
							     <td><%= status %></td>
							     <td><%= maintenance.getMachine().getId()%></td>
							     <td><%= maintenance.getMaintenanceLeader().getLastname()%></td>
						     	<% for(Report report : maintenance.getMaintenanceReports()){
						     			if(report.getReport()!=null){
						     				if(!report.getReport().isEmpty() && report.getWorker().getSerialNumber() == worker.getSerialNumber()){
						     	%>
						    	 <td>Yes<td> 
						    	 <% }}
						    	else {%>
						    	<td>No</td>
						    	<%}} %>
						    	<td><a class="btn btn-info" href="<%=str%>/maintenanceinfos?id=<%=maintenance.getMaintenanceId() %>" role="button">Consult</a></td>
						    </tr>
						    <%}}} %>
				 		</tbody>
				</table>
			</div> 
	</body>
</html>