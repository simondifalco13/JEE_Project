<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="be.project.javabeans.FactoryMachine" %>
<%@page import="be.project.javabeans.Order" %>
<%@page import="be.project.javabeans.Item" %>
<%@page import="java.util.ArrayList" %>
<%@ include file="Navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>List of the orders</title>
</head>
<body>
	<% ArrayList<Order> orders=(ArrayList<Order>) request.getAttribute("orders"); %>
	<div class="container">
		<% if(orders!=null){
		for(int i=0;i<orders.size();i++){
			Order order=orders.get(i);
			ArrayList<Item> items=order.getOrderItems();%>
			<div class="card m-2">
			  <div class="card-header">
			    Order <%=order.getOrderNumber() %>
			  </div>
			  <div class="card-body">
			    <h5 class="card-title">Order details</h5>
			    <p class="card-text">
			    	<table class="table table-hover">
			    		<thead>
						    <tr>
						      <th scope="col">Model</th>
						      <th scope="col">Brand</th>
						      <th scope="col">Supplier</th>
						      <th scope="col">Quantity</th>
						      <th scope="col">Price</th>
						    </tr>
						  </thead>
						  <tbody>
						  	<% for(int j=0;j<items.size();j++){
			    				Item currentItem=items.get(j);%>
									<tr>
								      <td><%=currentItem.getMachine().getModel() %></td>
								      <td><%=currentItem.getMachine().getBrand() %></td>
								      <td><%=currentItem.getMachine().getSupplier().getName() %></td>
								      <td><%=currentItem.getQuantity() %></td>
								      <td>
								      	<%=String.format("%.2f", currentItem.getMachine().getPrice()) %> euros
								      </td>
								    </tr>

			    			<%} %>
						  </tbody>
			    		
			    	</table>
			    	<span>Total order price : <b><%=order.getTotalPrice() %> euros</b></span>
			    	<br>
			    	<span>
			    		Ordered By <b><%=order.getEmployee().getLastname() %> <%=order.getEmployee().getFirstname() %></b>
			    	</span>
			    </p>
			  </div>
			</div>
		<%}
		}else{%>
			<p>No order created yet</p>
		<%} %>
	</div>
</body>
</html>