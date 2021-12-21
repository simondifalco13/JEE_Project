package be.project.api;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import be.project.dao.DatabaseConnection;
import be.project.models.User;
import be.project.utils.Error;

@Path("/user")
public class UserAPI {

	private Error error = null;
	public UserAPI() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getApiKey() {
		Context ctx;
		String api="";
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
		    api= (String) env.lookup("apiKey");
		} catch (NamingException e) {
			System.out.println("Error to get api key");
		}
		return api;
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("serialNumber") int matricule, 
			@FormParam("pwd") String password) {
		String responseJSON;
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
	
		boolean success= User.login(matricule, password);
		if(success) {
			responseJSON="{\"connected\":\"true\"}";
			String apiKey=getApiKey();
			return Response.status(Status.OK)
					.header("api-key", apiKey)
					.entity(responseJSON).build();
		}else {
			error=Error.USER_AUTHENTICATION_FAILED;
			error.setDescription("Invalid data for the login, verify your login and password");
			return Response.status(Status.OK).entity(error.getJSON()).build();
		}
		
	}
	
	@GET
	@Path("{serialNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("serialNumber") int serialNumber) {
		User user=User.getUser(serialNumber);
		return Response.status(Status.OK).entity(user).build();
	}

}
