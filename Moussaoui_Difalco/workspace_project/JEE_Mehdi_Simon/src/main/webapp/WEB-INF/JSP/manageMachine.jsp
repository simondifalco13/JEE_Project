<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Errors.jsp"%>
<%@page import="be.project.javabeans.FactoryMachine" %>
<%@page import="be.project.enumerations.OperationState" %>
<%@page import="java.lang.String" %>
<jsp:useBean id="machineToManage" class="be.project.javabeans.FactoryMachine" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<title>Manage Machine</title>
</head>
	<body>
		<% OperationState [] states=OperationState.values(); 
		   OperationState machineState=machineToManage.getOperationState();
		   %>
		<%!
		  public static String operationStateToString(OperationState state){
			   switch(state){
			   		case waitingForMaintenance :
			   			return "waiting for maintenance";
			   		case toReplace :
			   			return "to replace";
			   		default :
			   			return state.toString();	
			   }
		   }
		%>
		<%@ include file="Navbar.jsp" %>
		<div class="container">
			<h2>Manage machine </h2>
			<h4>Machine <%=machineToManage.getId() %> : <%=machineToManage.getModel() %></h4>
			<p>You can update the machine operation state : if you choose 'waiting for maintenance' 
			you'll be redirected to create the maintenance</p>
			<form action="ManageMachine" method="POST">
				<label for="selectState">Machine state : </label>
				<select class="form-select" aria-label="Select a state" id="selectState" name="state">
				<% for(int i=0;i<states.length;i++){
					if(states[i]==machineState){%>
						 <option selected><%=machineState.toString() %></option>
					<% }
					else{%>
						<option value="<%=states[i]%>"><%= operationStateToString(states[i]) %></option>
					<%}
				}%>
				</select>
				<div class="d-grid gap-2 col-6 mx-auto m-2">
					  <button type="submit" class="btn btn-primary">Validate</button>
				</div>
			</form>
			<div class="d-grid gap-2 col-6 mx-auto m-2">
					  <a href="machines" class="btn btn-primary">Cancel</a>
			</div>
		</div>
	</body>
</html>