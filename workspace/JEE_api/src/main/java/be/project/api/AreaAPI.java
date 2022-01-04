package be.project.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.AreaDAO;
import be.project.models.Area;

@Path("/area")
public class AreaAPI extends CommunAPI {

	private AreaDAO areaDAO=new AreaDAO();
	public AreaAPI() {
		// TODO Auto-generated constructor stub
	}
	
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		ArrayList<Area> areas=areaDAO.findAll();
		return Response.status(Status.OK).entity(areas).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArea(@PathParam("id") int id) {
		Area area=areaDAO.find(id);
		return Response.status(Status.OK).entity(area).build();
	}
	
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createArea(Area area) {
		Status status;

		boolean success=areaDAO.insert(area);
		if(!success) {
			status=Status.SERVICE_UNAVAILABLE;
			return Response.status(status).build();
		}else {
			status=Status.CREATED;
			return Response
					.status(status)
					.header("Location","/JEE_api/api/area/"+area.getId())
					.build();
		}
		
	}

//	@PUT
//	@Path("{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response modifyArea(Area area) {
//		
//		boolean success=areaDAO.update(area);
//		if(success) {
//			return Response
//					.status(Status.NO_CONTENT)
//					.build();
//		}
//		return Response
//				.status(Status.SERVICE_UNAVAILABLE)
//				.build();
//		
//	}
	
	@DELETE
	@Path("{id}")
	public Response deleteArea(@PathParam("id") int id) {
		boolean success=areaDAO.delete(id);
		if(success) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.SERVICE_UNAVAILABLE).build();
		}
	}

}
