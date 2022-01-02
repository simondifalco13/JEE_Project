<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Errors.jsp" session="true" import="be.project.javabeans.Maintenance"%>
    <%@page import="be.project.javabeans.Report" %>
    <%@page import="be.project.enumerations.MaintenanceStatus"  %>
    <%@page import="java.text.DateFormat"%>
    <%@page import="java.text.SimpleDateFormat" %>
    <%@ include file="Navbar.jsp" %>
    <jsp:useBean id="maintenance" class="be.project.javabeans.Maintenance" scope="session"></jsp:useBean>
<!DOCTYPE html>
<html>
	<head>
		<link href="css/style.css" rel="stylesheet" type="text/css">
		<meta charset="ISO-8859-1">
		<title>Maintenance détaillée</title>
	</head>
	<body>
		<% maintenance=(Maintenance)request.getAttribute("maintenance"); 
		%>
		<div class="title"><p>Details maintenance number : <%=maintenance.getMaintenanceId() %> </p></div>
		<% if(request.getAttribute("message")!=null){%>
			<div class="alert alert-success" style='text-align: center' role="alert">
  				<%= request.getAttribute("message") %>
			</div>
			<% } %>
		<div class="centered">
			<table class="table table-bordered" style="width: auto">
			<caption>Maintenance infos</caption>
			 		<thead>
	                     <tr>
	                     	<th>Maintenance number</th>
	                        <th>Maintenance date</th>
	                        <th>Start time</th>
	                        <th>End time</th>
	                        <th>Duration</th>
	                        <th>Maintenance status</th>
	                     </tr>
			 		</thead>
			 		<tbody>
					   	 <tr>
						     <td><%= maintenance.getMaintenanceId()%></td>
						     <% DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
						     String dateAsString = maintenance.getMaintenanceDate().toString();
						     String date = formatDate.format(maintenance.getMaintenanceDate());%>
						     <td><%= date%></td>
						     <% String debut = (maintenance.getStartTime() != null) ? String.valueOf(maintenance.getStartTime()) : "Maintenance has not started yet" ;%>
						     <td><%= debut%></td>
						     <% String fin = (maintenance.getEndTime() != null) ? String.valueOf(maintenance.getEndTime()) : "N/A" ;%>
						     <td><%= fin%></td>
						     <% String duration = (maintenance.getDuration() != null) ? maintenance.getDuration() : "N/A" ;%>
						     <td><%= duration%></td>
							 <% String status = (maintenance.getStatus() == MaintenanceStatus.todo) ? "TO DO" 
								: (maintenance.getStatus() == MaintenanceStatus.toredo) ? "TO REDO" 
								: (maintenance.getStatus() == MaintenanceStatus.ongoing) ? "ONGOING" 
								: (maintenance.getStatus() == MaintenanceStatus.done) ? "DONE" : "VALIDATED"
							 ;%>
						     <td><%= status%></td>	
					    </tr>
			 		</tbody>
			</table>
		<% if(maintenance.reportExist() == true & maintenance.reportAndStatusAllow()== true){ %>
			<div class="setdone"><a class="btn btn-success" href="<%=str%>/maintenancedone?id=<%=maintenance.getMaintenanceId()%>" role="button">Mark as done</a></div>
		<%}else if(!maintenance.reportAndStatusAllow()){ }
		else{%>
			<div class="setdone"><button class="btn btn-warning" disabled>To mark this maintenance as done, you need at least 1 written report</button></div>
			<%} %>
			<div class="back" class="d-grid gap-2 col-6 mx-auto m-4">
    		 	<a href="<%=str%>/home" class="btn btn-primary">Back</a>
    		</div>
		</div>
		<!-- Informations personnes concernées -->
		<div class="divtable2">
		<table class="table table-bordered" style="width: auto">
			<caption>People concerned</caption>
			<colgroup>
	        	<col>
	        	<col span="2" class="col1">
	        	<col span="1" class="col2">
   			 </colgroup>
		 	<% int nbrWorkers = maintenance.getMaintenanceWorkers().size()+1;%>
		 		<tr>
			        <td></td>
			        <th scope="col">Lastname</th>
			        <th scope="col">Firstname</th>
			        <th scope="col">SerialNumber</th>
	  			</tr>
				<tr>
			        <th scope="row">Leader</th>
			        <td><%= maintenance.getMaintenanceLeader().getLastname() %></td>
			        <td><%= maintenance.getMaintenanceLeader().getFirstname() %></td>
			        <td><%= maintenance.getMaintenanceLeader().getSerialNumber() %>
				</tr>
				<tr>
			        <th scope="row" rowspan="<%=nbrWorkers%>">Workers</th>
			    </tr>
			    <% for(Worker workerMaintenance : maintenance.getMaintenanceWorkers()) {%>
			    <tr>
			        <td><%= workerMaintenance.getLastname() %></td>
			        <td><%= workerMaintenance.getFirstname() %></td>
			        <td><%= workerMaintenance.getSerialNumber() %></td>
				</tr>
		 	<%} %>
		</table>
		</div>
		<div class="divtable3">
		<table class="table table-bordered" class="tabDetails3" style="width: auto">
			<caption>Report list</caption>
			<% for(Report report : maintenance.getMaintenanceReports()){
			%>
		 		<tr>
			        <td><ul><li><%= report.getWorker().getLastname() %>  <%=report.getWorker().getFirstname()%> :
			        <% if(report.getReport()!=null && !report.getReport().isBlank()){
				     	%>
			        <textarea class="rapport" readonly><%=report.getReport()%></textarea><%}else{ %>
			        Any report submitted
			        <!-- SI aucun rapport + utilisateur = worker parcouru -->
			        <% if(report.getWorker().getSerialNumber() == user.getSerialNumber() && maintenance.reportAndStatusAllow() == true){%>
			        <br><a href="<%=str%>/writereport?id=<%=maintenance.getMaintenanceId()%>">Write a report</a>
			        <%} %>
			        </li></ul></td>
	  				<%}} %>
	  			</tr>
	  		
		</table>
		</div>

		<div class="centered">
		<table class="table table-bordered" style="width: auto">
		<caption>Affected machine</caption>
				   	 <tr>
						<td>Machine number :<%=maintenance.getMachine().getId() %></td>
						<td>Machine type : <%=maintenance.getMachine().getType() %></td>
						<td>Brand : <%= maintenance.getMachine().getBrand() %></td>
						<td>Model : <%= maintenance.getMachine().getModel() %></td>
				    </tr>
		</table>
		</div>
	</body>
</html>