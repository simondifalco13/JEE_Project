package be.project.api;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.models.FactoryMachine;
import be.project.models.Order;

@Path("/order")
public class OrderAPI extends CommunAPI{

	public OrderAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrders(@HeaderParam("key") String key) {
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			ArrayList<Order> orders=Order.getAllOrders();
			return Response.status(Status.OK).entity(orders).build();
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

}
