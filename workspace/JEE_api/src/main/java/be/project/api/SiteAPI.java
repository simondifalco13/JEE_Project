package be.project.api;

import javax.ws.rs.Path;
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

import be.project.dao.SiteDAO;
import be.project.models.Area;
import be.project.models.Site;

@Path("/site")
public class SiteAPI {

	private SiteDAO siteDAO=new SiteDAO();
	
	public SiteAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		System.out.println("1");
		ArrayList<Site> sites=siteDAO.findAll();
		return Response.status(Status.OK).entity(sites).build();
	}
	
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSite(@PathParam("id") int id) {
		Site site=siteDAO.find(id);
		return Response.status(Status.OK).entity(site).build();
	}
	
	

}
