package be.project.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Employee;
import be.project.models.Leader;

@Path("/leader")
public class LeaderAPI {

	public LeaderAPI() {
		// TODO Auto-generated constructor stub
	}

	//verifier qu'on recoit bien la clé de l'api
		@GET
		@Path("{serialNumber}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getLeader(@PathParam("serialNumber") int serialNumber) {
			Leader leader=Leader.getLeader(serialNumber);
			return Response.status(Status.OK).entity(leader).build();
		}
}
