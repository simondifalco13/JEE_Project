<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="be.project.javabeans.Maintenance" errorPage="Errors.jsp"%>
    <%@ include file="Navbar.jsp" %>
    <jsp:useBean id="maintenance" class="be.project.javabeans.Maintenance" scope="session"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<meta charset="ISO-8859-1">
	<title>Report creation</title>
</head>
	<body>
		<div class="container" >
			<h2 class="title">Report creation</h2>
			<% if(request.getAttribute("error")!=null){%>
			<div class="alert alert-danger" role="alert">
  				<%= request.getAttribute("error") %>
			</div>
			<% } %>
			<% int maintenance_id = (int)request.getAttribute("maintenanceid");%>
			<form action="writereport" method="POST">
			  <div class="mb-3">
			    <label for="inputReport" class="form-label">Report :</label>
			    <textarea class="form-control" id="inputReport" name="report" rows="15" cols="35" maxlength="900"
			    aria-describedby="emailHelp" required></textarea>
			  </div>
			  <input type="hidden" name="maintenanceid" value="<%=maintenance_id%>"/>
			  <button type="submit" class="btn btn-primary" >Confirm</button>
			</form>
			<div class="back2">
					<a href="<%=str%>/maintenanceinfos" class="btn btn-primary">Back</a>
  			</div>
		</div>
		
			<table class="table table-bordered centered" style="width: auto">
				<caption>Further informations</caption>
				   	 <tr>
						<td>Maintenance number :<%=maintenance_id %></td>
					</tr>
					<tr>
						<td>Writing worker : 	
						<ul>
							<li>Serialnumber : <%=user.getSerialNumber() %></li>
							<li>Lastname : <%=user.getLastname() %></li>
							<li>Firstname : <%=user.getFirstname()%></li>
						</ul></td>
				    </tr>
			</table>
	</body>
</html>