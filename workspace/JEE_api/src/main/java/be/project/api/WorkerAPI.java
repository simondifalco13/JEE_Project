package be.project.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Leader;
import be.project.models.Worker;

@Path("/worker")
public class WorkerAPI extends CommunAPI {

	public WorkerAPI() {
		// TODO Auto-generated constructor stub
	}
	
	//verifier qu'on recoit bien la clé de l'api
	@GET
	@Path("{serialNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorker(@PathParam("serialNumber") int serialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			Worker worker=Worker.getWorker(serialNumber);
			return Response.status(Status.OK).entity(worker).build();
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

}
