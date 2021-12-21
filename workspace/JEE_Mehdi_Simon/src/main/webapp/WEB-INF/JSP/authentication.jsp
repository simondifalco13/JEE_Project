<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<title>Authentication</title>
</head>
<body>
	<div class="container" >
		<h2>Authentication</h2>
		<form action="authentication" method="POST">
		  <div class="mb-3">
		    <label for="inputSerialNumber" class="form-label">Serial number</label>
		    <input type="text" class="form-control" id="inputSerialNumber" name="serialNumber" aria-describedby="emailHelp">
		    <div id="serialNumber" class="form-text">Please enter your serial number.</div>
		  </div>
		  <div class="mb-3">
		    <label for="inputPassword" class="form-label">Password</label>
		    <input type="password" class="form-control" id="inputPassword" name="password">
		  </div>
		  <button type="submit" class="btn btn-primary" >Submit</button>
		</form>
	</div>
	
</body>
</html>