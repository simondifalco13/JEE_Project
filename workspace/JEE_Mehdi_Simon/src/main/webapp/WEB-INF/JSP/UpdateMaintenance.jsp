<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.Maintenance" %>
<%@page import="be.project.enumerations.MaintenanceStatus" %>
<%@page import="java.lang.String" %>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="connectedUser" class="be.project.javabeans.Leader" scope="session"></jsp:useBean>
<jsp:useBean id="maintenance" class="be.project.javabeans.Maintenance" scope="request"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Update maintenance</title>
</head>
<body>
	<%
		MaintenanceStatus[] maitenanceStatus=MaintenanceStatus.values();
	%>
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
		
		%>
	<%@ include file="Navbar.jsp" %>
	<div class="container">
		<h3 class="text-center">
				<b>Maintenance <%=maintenance.getMaintenanceId() %></b>
		</h3>
		<b>Current Status :</b> <%=maintenanceStatusInStringFormat(maintenance.getStatus())%><br>
		<form action="UpdateMaintenance" method="POST">
			<label for="selectState">Maintenance status : </label>
			<select class="form-select" aria-label="Select a state" id="selectStatus" name="status">
			<% for(int i=0;i<maitenanceStatus.length;i++){
				if(maitenanceStatus[i]!=maintenance.getStatus()){%>
					 <option value="<%=maitenanceStatus[i]%>"><%=maintenanceStatusInStringFormat(maitenanceStatus[i]) %></option>
				<% }
			}%>
			</select>
			<div class="d-grid gap-2 col-6 mx-auto m-2">
				  <button type="submit" class="btn btn-primary">Validate</button>
			</div>
		</form>
		<div class="d-grid gap-2 col-6 mx-auto m-2">
				  <a href="ConsultMaintenance" class="btn btn-primary">Cancel</a>
		</div>
			
	</div>
</body>
</html>