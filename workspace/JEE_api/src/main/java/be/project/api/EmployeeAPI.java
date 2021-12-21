package be.project.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Employee;
import be.project.models.Worker;

@Path("/employee")
public class EmployeeAPI {

	public EmployeeAPI() {
		// TODO Auto-generated constructor stub
	}
	
	//verifier qu'on recoit bien la clé de l'api
	@GET
	@Path("{serialNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee(@PathParam("serialNumber") int serialNumber) {
		Employee employee=Employee.getEmployee(serialNumber);
		return Response.status(Status.OK).entity(employee).build();
	}

}
