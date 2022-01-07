<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Errors.jsp" session="true" import="be.project.javabeans.Maintenance"%>
    <%@page import="be.project.javabeans.Report" %>
    <%@page import="be.project.enumerations.MaintenanceStatus"  %>
    <%@page import="java.text.DateFormat"%>
    <%@page import="java.text.SimpleDateFormat" %>
    <%@page import="java.util.Date" %>
    <%@ include file="Navbar.jsp" %>
    <jsp:useBean id="maintenance" class="be.project.javabeans.Maintenance" scope="session"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<meta charset="ISO-8859-1">
	<title>Maintenance details</title>
</head>
	<body>
		<% maintenance=(Maintenance)request.getAttribute("maintenance"); 
		%>
		<%!	public boolean reportAndStatusAllow(Maintenance maintenance) {
				boolean allow=false;
				if(maintenance.getStatus()== MaintenanceStatus.ongoing || maintenance.getStatus()== MaintenanceStatus.todo || maintenance.getStatus()== MaintenanceStatus.toredo) {
					allow =true;
				}
				return allow;
		} 
			public boolean dateAllow(Maintenance maintenance){
				Date currentDate = new Date();
				Date currentDateFinal=new Date(currentDate.getDate(),currentDate.getMonth(),currentDate.getYear());
				Date maintenanceDate = maintenance.getMaintenanceDate();
				Date maintenanceDateFinal=new Date(maintenanceDate.getDate(),maintenanceDate.getMonth(),maintenanceDate.getYear());
				String currentDateToString = dateFormat.format(currentDate);
				String maintenanceDateToString = dateFormat.format(maintenanceDate);
				if(maintenanceDateFinal.compareTo(currentDateFinal)<0) {
					return true;
				}
				else return false;
			}
			 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");					
		%>
		
		
		<div class="title"><p>Details maintenance number : <%=maintenance.getMaintenanceId() %> </p></div>
		<% if(request.getAttribute("message")!=null){%>
			<div class="alert alert-success" style='text-align: center' role="alert">
  				<%= request.getAttribute("message") %>
			</div>
			<% } %>
			<table class="table table-bordered centered caption-top" style="width: auto">
			<caption class="text-dark">Maintenance infos</caption>
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
		<% if(maintenance.reportExist() == true & reportAndStatusAllow(maintenance)== true && dateAllow(maintenance)==true){ %>
			<div class="setdone">
				<form action="maintenanceinfos" method="POST">
					<button type="submit" class="btn btn-success" name="maintenanceidfordone" value="<%=maintenance.getMaintenanceId()%>">Mark as done</button>
				</form>
			</div>
		<%}else if(!reportAndStatusAllow(maintenance)){ }
		else if(!dateAllow(maintenance)){%>
			<div class="setdone"><button class="btn btn-warning" disabled>This maintenance has not started yet</button></div>
		<%}
		else{%>
			<div class="setdone"><button class="btn btn-warning" disabled>To mark this maintenance as done, you need at least 1 written report</button></div>
			<%} %>
			<div class="back" style="margin-bottom : 10px">
    		 	<a href="<%=str%>/home" class="btn btn-primary">Back</a>
    		</div>
		<!-- Informations personnes concern�es -->
		<table class="centered" style="margin-bottom : 10px"><tr><td>
				<table class="table table-bordered centered" style="width: auto">
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
			</td><td>
				<table class="table table-bordered" style="width: auto">
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
				        <% if(report.getWorker().getSerialNumber() == user.getSerialNumber() && reportAndStatusAllow(maintenance) && dateAllow(maintenance)){%>
				        <br>
				        <form action="maintenanceinfos" method="POST">
							<button type="submit" class="btn btn btn-link" name="maintenanceid" value="<%=maintenance.getMaintenanceId()%>">Write a report</button>
						</form>
				        <%} %>
				        </li></ul></td>
		  				<%}} %>
		  			</tr>
			</table>
			</td></tr>
		</table>
		</table>

		<table class="table table-bordered centered caption-top" style="width: auto">
		<caption class="text-dark">Affected machine</caption>
				   	 <tr>
					   	 <th>Machine number</th>
					   	 <th>Machine type</th>
					     <th>Brand</th>
					 	 <th>Model</th>
				    </tr>
				    <tr>
				    	<td><%=maintenance.getMachine().getId() %></td>
						<td><%=maintenance.getMachine().getType() %></td>
						<td><%= maintenance.getMachine().getBrand() %></td>
						<td><%= maintenance.getMachine().getModel() %></td>
				    </tr>
		</table>
	</body>
</html>