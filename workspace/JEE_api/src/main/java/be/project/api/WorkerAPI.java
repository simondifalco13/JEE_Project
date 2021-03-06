package be.project.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Leader;
import be.project.models.Maintenance;
import be.project.models.Worker;

@Path("/worker")
public class WorkerAPI extends CommunAPI {

	public WorkerAPI() {
		// TODO Auto-generated constructor stub
	}
	
	//verifier qu'on recoit bien la cl? de l'api
	@GET
	@Path("{serialNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorker(@PathParam("serialNumber") int serialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				Worker worker=Worker.getWorker(serialNumber);
				return Response.status(Status.OK).entity(worker).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	@GET
	@Path("{serialNumber}/maintenances")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMaintenances(@PathParam("serialNumber") int serialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				ArrayList<Maintenance> maintenances= Worker.getMaintenances(serialNumber);
				return Response.status(Status.OK).entity(maintenances).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
}
