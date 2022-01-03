<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Errors.jsp"%>
 <%@ include file="Navbar.jsp" %>
 	<%@page import="be.project.enumerations.MachineType"  %>
 	<%@page import="be.project.javabeans.SupplierMachine"  %>
	<%@page import="java.util.ArrayList" %>
	<%@page import="java.util.Set" %>
	<%@page import="java.util.HashSet" %>

<!DOCTYPE html>
<html>
<head>
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<meta charset="ISO-8859-1">
	<title>Suppliers machines</title>
</head>
	<body>
		<% 	String type= (String)request.getAttribute("type");
			ArrayList<SupplierMachine> machines=(ArrayList<SupplierMachine>)request.getAttribute("machines");
			Set<String> supplierNameList = new HashSet<>();
			for(SupplierMachine machine : machines){
				supplierNameList.add(machine.getSupplier().getName());}
			int index =0;
		%>

		<fieldset>
			<legend>Suppliers machines <%=type%> type</legend>

				<%for(String name : supplierNameList ){%>
				<div class="divtable2">
					<table class="table table-bordered" style="width: auto">
					<caption><%=name%> machines</caption>
					<tr>
						<th>Model</th>
						<th>Brand</th>
						<th>Description</th>
						<th>Price</th>
					<tr>
					<%for(SupplierMachine machine : machines){ %>
						<% if(machine.getSupplier().getName().equals(name)){%>
					<tr>
						<td><%=machine.getModel() %></td>
						<td><%=machine.getBrand() %></td>
						<td><%= machine.getDescription() %></td>
						<td><%= machine.getPrice() %> euros</td>
					<tr>			
					<%}}%>
					</table>
				</div>	
				<%}%>
		</fieldset>
	</body>
</html>