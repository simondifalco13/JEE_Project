<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Errors.jsp" session="true"%>
 <%@ include file="Navbar.jsp" %>
 	<%@page import="be.project.enumerations.MachineType"  %>
 	<%@page import="be.project.javabeans.SupplierMachine"  %>
 	<%@page import="be.project.javabeans.FactoryMachine"  %>
	<%@page import="java.util.ArrayList" %>
	<%@page import="java.util.Set" %>
	<%@page import="java.util.HashSet" %>
	<%@page import="java.text.NumberFormat" %>
	<%@page import="java.util.Locale" %>
	

<!DOCTYPE html>
<html>
<head>
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<meta charset="ISO-8859-1">
	<title>Suppliers machines page</title>
</head>
	<body>
		<% 	FactoryMachine machineToReplace=(FactoryMachine) session.getAttribute("machineToReplace");
			String type= (String)request.getAttribute("type");
			ArrayList<SupplierMachine> machines=(ArrayList<SupplierMachine>)request.getAttribute("machines");
			Set<String> supplierNameList = new HashSet<>();
			for(SupplierMachine machine : machines){
				supplierNameList.add(machine.getSupplier().getName());}
			int index =0;
		%>
		<%! public String numberFormat(double nbr){
			Locale belgium = new Locale("fr", "BE");
			NumberFormat eurosFormat = NumberFormat.getCurrencyInstance(belgium);
			return String.valueOf(eurosFormat.format(nbr));
			}
		%>
		

		<fieldset>
			<legend>Research machines by type</legend>
			<div class="divform">
				<form action="suppliersmachines" method="post">
					<div>
						<label>Machine's type</label>
					</div>
					<input list="machinetypes" name="machinetype"> 
					<datalist id="machinetypes" itemtype="text">
						<option value="Assembly">
						<option value="Sorting">
						<option value="Production">
					</datalist>
					<span><button type="submit" class="btn btn-primary">Research</button></span>
				</form>
			</div>
		
		</fieldset>
		<fieldset>
			<legend>Suppliers machines <%=type%> type</legend>

				<%for(String name : supplierNameList ){%>
					<table class="table table-bordered centered" style="width: auto">
					<caption><%=name%> machines</caption>
					<tr>
						<th>Model</th>
						<th>Brand</th>
						<th>Description</th>
						<th>Price</th>
					<tr>
					<%for(SupplierMachine machine : machines){%>
						<% if(machine.getSupplier().getName().equals(name)){%>
					<tr>
						<td><%=machine.getModel() %></td>
						<td><%=machine.getBrand() %></td>
						<%if(machine.getDescription()==null || machine.getDescription().isBlank()){ %>
						<td>Aucune description disponible</td>
						<%}else{ %>
						<td><%= machine.getDescription()%></td><%} %>
		
						<td><%=String.format("%.2f", machine.getPrice()) %> euros</td>
						<% if(machine.getType().equals(machineToReplace.getType())){%>
						<td><form action="suppliersmachines" method="post">
								<button type="submit" name="suppliermachineid" value="<%= machine.getId()%>">Order this machine</button>
							</form>
						</td><%} %>
					<tr>			
					<%}}%>
					</table>
				<%}%>
				<div class="back">
    		 		<a href="<%=str%>/maintenances" class="btn btn-primary">Back</a>
    			</div>
		</fieldset>
	</body>
</html>