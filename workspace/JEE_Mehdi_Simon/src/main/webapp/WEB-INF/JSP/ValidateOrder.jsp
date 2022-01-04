<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
    <%@ include file="Navbar.jsp" %>
  	<%@page import="be.project.javabeans.SupplierMachine"  %>
       <jsp:useBean id="supplierMachine" class="be.project.javabeans.SupplierMachine" scope="session"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<meta charset="ISO-8859-1">
	<title>Order validation page</title>
</head>
<body>
	<% supplierMachine=(SupplierMachine)request.getAttribute("suppliermachine"); %>

	<div class="title"><%= user.getLastname() %>, here is the summary of the order</div>
	<% if(request.getAttribute("error")!=null){%>
			<div class="alert alert-danger" role="alert">
  				<%= request.getAttribute("error") %>
			</div><% } %>
			
		<table class="table table-bordered centered border" style="width: auto">
		<caption>Summary</caption>
		
				   	 <tr class="border">
					   	 <th colspan="4">Machine infos</th>
				    </tr>
				    <tr>
				    	<td>Brand</td>
						<td>Model</td>
						<td>Description</td>
						<th>Price</th>
				    </tr>
				    <tr>
				    	<td><%= supplierMachine.getBrand() %></td>
				    	<td><%= supplierMachine.getModel() %></td>
				    	<%if(supplierMachine.getDescription()!=null && !supplierMachine.getDescription().isBlank()){%>
				    	<td><%= supplierMachine.getDescription()%></td><%}else{%>
				    	<td>No description available</td><%} %>
				    	<th><%=String.format("%.2f", supplierMachine.getPrice())%> euros</th>
				    </tr>
				     <tr class="border">
					   	 <th colspan="4">Supplier</th>
				    </tr>
				    <tr>
				    	<td colspan="2">Number</td>
				    	<td colspan="2">Name</td>
				    </tr>
				    <tr>
				    	<td colspan="2"><%=supplierMachine.getSupplier().getId() %></td>
				    	<td colspan="2"><%=supplierMachine.getSupplier().getName() %></td>
				    </tr>
		</table>
		<div style="text-align: center">
			<div style="margin-bottom: 5px">
				<form action="createorder" method="post">
					<button type="submit" name="suppliermachineid" value="<%=supplierMachine.getId()%>"class="btn btn-primary">Confirm</button>
				</form>
			</div>
    		<a href="<%=str%>/maintenances" class="btn btn-danger">Cancel</a>
		</div>
</body>
</html>