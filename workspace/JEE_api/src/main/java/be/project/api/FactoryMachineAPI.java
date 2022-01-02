package be.project.api;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.enumerations.OperationState;
import be.project.models.FactoryMachine;
import be.project.models.Machine;

@Path("/factory/machine")
public class FactoryMachineAPI extends CommunAPI  {

	public FactoryMachineAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFactoryMachinesBySite(@DefaultValue("1") @QueryParam("site") int siteId,
			@HeaderParam("key") String key) {
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			ArrayList<FactoryMachine> machines=FactoryMachine.getAllFactoryMachinesBySite(siteId);
			return Response.status(Status.OK).entity(machines).build();
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFactoryMachines(@HeaderParam("key") String key) {
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			ArrayList<FactoryMachine> machines=FactoryMachine.getAllFactoryMachines();
			return Response.status(Status.OK).entity(machines).build();
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateFactoryMachine(@PathParam("id") int id,
			
			@FormParam("id") int machine_id,
			@FormParam("operationState") String operationState,
			@HeaderParam("key") String key) {
		FactoryMachine machine=new FactoryMachine();
		machine.setId(machine_id);
		machine.setOperationState(OperationState.valueOf(operationState));
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			boolean success=machine.update();
			if(success) {
				return Response.status(Status.NO_CONTENT).build();
			}else {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

}
