package be.project.api;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.models.FactoryMachine;
import be.project.models.SupplierMachine;

@Path("/supplier/machine")
public class SupplierMachineAPI extends CommunAPI{

	public SupplierMachineAPI() {
		// TODO Auto-generated constructor stub
	}

		
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSupplierMachine(@PathParam("id") int id,
			@HeaderParam("key")String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				SupplierMachine machine = new SupplierMachine();
				machine= SupplierMachine.getSupplierMachine(id);
				return Response.status(Status.OK).entity(machine).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Path("type/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSuppliersMachines(@PathParam("type")String type, 
			@HeaderParam("key") String key) {
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<SupplierMachine> machines=SupplierMachine.getAllSuppliersMachines(type);
				return Response.status(Status.OK).entity(machines).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	

}
