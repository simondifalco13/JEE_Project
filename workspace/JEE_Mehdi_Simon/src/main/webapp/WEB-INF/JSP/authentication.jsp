<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Authentication</title>
</head>
<body>
	<%@ include file="Navbar.jsp" %>
	<div class="container" >
		<div class="row justify-content-center">
		    <div class="col-3">
		      <h2>Authentication</h2>
		    </div>
		 </div>
		<% if(request.getAttribute("error")!=null){%>
			<div class="alert alert-danger" role="alert">
  				<%= request.getAttribute("error") %>
			</div>
		<% } %>
		<form action="authentication" method="POST">
		  <div class="mb-3">
		    <label for="inputSerialNumber" class="form-label">Serial number</label>
		    <input type="text" class="form-control" id="inputSerialNumber" name="serialNumber" aria-describedby="emailHelp" required>
		    <div id="serialNumber" class="form-text" class="invalid-feedback">Please enter your serial number.</div>
		  </div>
		  <div class="mb-3">
		    <label for="inputPassword" class="form-label">Password</label>
		    <input type="password" class="form-control" id="inputPassword" name="password" required>
		    <div id="password" class="form-text" class="invalid-feedback">Please enter your password.</div>
		  </div>
		  <button type="submit" class="btn btn-primary" >Submit</button>
		</form>
	</div>
</body>
</html>