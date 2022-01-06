<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Errors.jsp"%>
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
<jsp:useBean id="connectedUser" class="be.project.javabeans.Leader" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<title>Machines page</title>
</head>
	<body>
		<% ArrayList<FactoryMachine> machines=(ArrayList<FactoryMachine>)request.getAttribute("machines");
		
		%>
		<%!
			public static String operationStateInStringFormat(OperationState state){
				switch(state){
					case waitingForMaintenance:
						return "waiting for maintenance";
					case toReplace:
						return "to replace";
					default: 
						return state.toString();
				}
			}
		
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
		%>
		<%@ include file="Navbar.jsp" %>
		<div class="container">
			<h4>Welcome on the machines pages</h4>
			<div class="row align-items-start">
				<% for(int i=0;i<machines.size();i++){
						%>
						<div class="card col m-2" style="width: 18rem;">
						  <div class="card-body">
						    <h5 class="card-title text-center"><b><%= machines.get(i).getModel() %></b></h5>
						    <p class="card-text">
						    	<b>Brand</b> : <%=machines.get(i).getBrand() %><br>
						    	<b>Type</b> : <%=machines.get(i).getType() %>
						    	<% if(machines.get(i).getDescription()!=null && !machines.get(i).getDescription().equals("")){
						    		 %>
						    		 	<b>Description</b> : <%=machines.get(i).getDescription() %>
						    		 <%
						    	} %>
						    </p>
						    <p class="card-text">
						    	<b>Status</b> : <%= operationStateInStringFormat(machines.get(i).getOperationState()) %>
						    	<br>
						    	
						    </p>
						    <div>
						    	<p class="card-text"><b>In the area(s) </b>:</p>
						    	<ul>
						    		<% for(int j=0;j<machines.get(i).getMachineAreas().size();j++){
						    			Area currentArea=machines.get(i).getMachineAreas().get(j);
						    			%>
						    				<li>
						    					Area <%=currentArea.getSection() %>
												(<%=currentArea.getDangerousness() %>)
						    				</li>
						    			<%
						    			
						    	} %>
						    	</ul>
						    </div>
						    <div class="m-2">
						    	<p class="card-text"><b>Maintenance(s)</b>:</p>
						    	<details>
									<summary>Show maintenances</summary>
									<% for(int k=0;k<machines.get(i).getMachineMaintenances().size();k++) {
						    		Maintenance currentMaintenance=machines.get(i).getMachineMaintenances().get(k);
						    		SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
						    		%>
						    			<div class="border border-primary p-2 m-2">
						    			 	<h5 class="text-center"><b>Maintenance <%=currentMaintenance.getMaintenanceId() %></b></h5>
						    				<b>Status :</b> <%=maintenanceStatusInStringFormat(currentMaintenance.getStatus())%><br>
						    				<b>Date :</b> <%=DateFor.format(currentMaintenance.getMaintenanceDate()) %> <br>
						    				<b>Start :</b> <%=currentMaintenance.getStartTime().format(dateTimeFormatter) %> <br>
						    				<% if(currentMaintenance.getEndTime()!=null){ %>
						    					<b>End : </b> <%=currentMaintenance.getEndTime().format(dateTimeFormatter) %> <br>
						    					<b>Duration : </b><%=currentMaintenance.getDuration() %> <br>
						    				<%}%>
						    				Supervised by <%=currentMaintenance.getMaintenanceLeader().getLastname()%> <%=currentMaintenance.getMaintenanceLeader().getFirstname()%>  (serial number : <%=currentMaintenance.getMaintenanceLeader().getSerialNumber()%>)<br>
						    				<b>Worker(s) : </b>
						    				<% if(currentMaintenance.getMaintenanceWorkers().size()>0){%>
												<ul>
							    					<%for(int m=0;m<currentMaintenance.getMaintenanceWorkers().size();m++){
							    						Worker maintenanceWorker=currentMaintenance.getMaintenanceWorkers().get(m);%>
							    						<li>
							    							<%=maintenanceWorker.getFirstname() %> <%=maintenanceWorker.getLastname() %> (serial number : <%=maintenanceWorker.getSerialNumber() %>)
							    						</li>
							    					<%} %>
						    					</ul>
						    				<%}else{%>
						    					workers not yet assigned
						    				<%}%>
						    				<form action="machines" method="POST">
						    					 <button type="submit" class="btn btn-primary" name="maintenance" value="<%=currentMaintenance.getMaintenanceId() %>" >Consult</button>
						    				</form>
						    			</div>
						    		<%
						    	}%>
								</details>
						    	
						    </div>
						   
					    	<form action="machines" method="POST">
					    	 	<div class="d-grid gap-2 col-6 mx-auto">
					    		 	<button type="submit" class="btn btn-primary" name="machine" value="<%=machines.get(i).getId()%>">Manage</button>
					    		</div>
					    	</form>
							
						  </div>
						</div>
						<%}%>
			</div>
		</div>
	</body>
</html>