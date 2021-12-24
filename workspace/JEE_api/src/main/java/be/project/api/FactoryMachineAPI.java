package be.project.api;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.models.FactoryMachine;

@Path("/factory/machine")
public class FactoryMachineAPI extends CommunAPI  {

	public FactoryMachineAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFactoryMachines(@QueryParam("site") int siteId,
			@HeaderParam("key") String key) {
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			ArrayList<FactoryMachine> machines=FactoryMachine.getAllFactoryMachines(siteId);
			return Response.status(Status.OK).entity(machines).build();
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

}
