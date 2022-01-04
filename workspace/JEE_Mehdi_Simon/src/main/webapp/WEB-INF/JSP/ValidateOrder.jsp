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
		<table class="table table-bordered centered" style="width : auto">
		<caption>Summary</caption>
				<tr>
					<th class="border">Machine infos</th>
				</tr>
				<tr>
					<td>
						<ul>
							<li>Brand: <%= supplierMachine.getBrand() %></li>
							<li>Model :<%= supplierMachine.getModel() %></li>
							<%if(supplierMachine.getDescription()!=null && !supplierMachine.getDescription().isBlank()){ %>
							<li>Description : <%= supplierMachine.getDescription()%></li><%}%>
							<li>Price : <%=String.format("%.2f", supplierMachine.getPrice()) %> euros</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th class="border">Supplier<th>
				</tr>
				<tr>	
					<td>
						<ul>
							<li>Supplier number : <%=supplierMachine.getSupplier().getId() %></li>
							<li>Supplier name : <%=supplierMachine.getSupplier().getName() %></li>
						</ul>
					</td>
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