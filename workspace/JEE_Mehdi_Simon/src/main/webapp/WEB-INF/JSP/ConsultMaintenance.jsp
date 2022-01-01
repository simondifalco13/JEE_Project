<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.Maintenance" %>
<%@page import="be.project.javabeans.Worker" %>
<%@page import="be.project.javabeans.Report" %>
<%@page import="be.project.enumerations.MaintenanceStatus" %>
<%@page import="java.lang.String" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="connectedUser" class="be.project.javabeans.Leader" scope="session"></jsp:useBean>
<jsp:useBean id="maintenance" class="be.project.javabeans.Maintenance" scope="request"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Consult Maintenance</title>
</head>
<body>
	<%!
		
		public static String maintenanceStatusInStringFormat(MaintenanceStatus state){
			switch(state){
				case todo:
					return "to do";
				case toredo:
					return "to redo";
				default: 
					return state.toString();
			}
		}
		
		 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		 SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
		%>
	<%@ include file="Navbar.jsp" %>
	<div class="container">
		<% if(request.getAttribute("error")!=null){%>
			<div class="alert alert-danger" role="alert">
  				<%= request.getAttribute("error") %>
			</div>
		<% } %>
		<div class="col-6 mx-auto">
			<h3 class="text-center">
				<b>Maintenance <%=maintenance.getMaintenanceId() %></b>
			</h3>
			<b>Status :</b> <%=maintenanceStatusInStringFormat(maintenance.getStatus())%><br>
			<b>Date :</b> <%=DateFor.format(maintenance.getMaintenanceDate()) %> <br>
			<b>Start :</b> <%=maintenance.getStartTime().format(dateTimeFormatter) %> <br>
			<% if(maintenance.getEndTime()!=null){ %>
				<b>End : </b> <%=maintenance.getStartTime().format(dateTimeFormatter) %> <br>
				<b>Duration : </b><%=maintenance.getDuration() %> <br>
			<%}%>
	  		Supervised by <%=maintenance.getMaintenanceLeader().getLastname()%> <%=maintenance.getMaintenanceLeader().getFirstname()%>  (serial number : <%=maintenance.getMaintenanceLeader().getSerialNumber()%>)<br>
	  		<b>Worker(s) : </b>
	  		<% if(maintenance.getMaintenanceWorkers().size()>0){%>
			<ul>
	   		<%for(int m=0;m<maintenance.getMaintenanceWorkers().size();m++){
	   				Worker maintenanceWorker=maintenance.getMaintenanceWorkers().get(m);%>
	   				<li>
	   					<%=maintenanceWorker.getFirstname() %> <%=maintenanceWorker.getLastname() %> (serial number : <%=maintenanceWorker.getSerialNumber() %>)
	   				</li>
	   		<%} %>
	  		</ul>
	  		<%}else{%>
	  			workers not yet assigned
	  		<%}%>
	  		<b>Report(s) :</b>
	  		<% if(maintenance.getMaintenanceReports()!=null){
	  			if(maintenance.getMaintenanceReports().size()>0){%>
		  			<div class="border border-info m-2">
		  				<%for(int n=0;n<maintenance.getMaintenanceReports().size();n++){
		  					Report currentReport=maintenance.getMaintenanceReports().get(n);%>
		  						<%=currentReport.getReport() %><br>
		  						By : <%=currentReport.getWorker().getFirstname() %> <%=currentReport.getWorker().getLastname() %>
		  				<%} %>
		  			</div>
		  		<%}else{%>
		  			no reports created yet;
		  		<%}
		  	}else{%>
		  		no reports created yet;
		  	<%}%>
		</div>	
		<form action="ConsultMaintenance" method="POST">
    	 	<div class="d-grid gap-2 col-6 mx-auto m-2">
    		 	<button type="submit" class="btn btn-primary" name="maintenance" value="<%=maintenance.getMaintenanceId()%>">Modify status</button>
    		</div>
		</form>
		<div class="d-grid gap-2 col-6 mx-auto m-4">
    		 	<a href="machines" class="btn btn-primary">Back</a>
    	</div>
		
	</div>
</body>
</html>