package be.project.api;

import java.sql.Connection;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.models.User;
import be.project.utils.Error;

@Path("/user")
public class UserAPI {

	private Error error = null;
	public UserAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("matricule") int matricule, 
			@FormParam("pwd") String password) {
		
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			return Response.status(Status.OK).entity(DatabaseConnection.getError()).build();
		}
	
		User user= User.login(matricule, password);
		if(user!=null) {
			return Response.status(Status.OK).entity(user).build();
		}else {
			error=Error.USER_AUTHENTICATION_FAILED;
			error.setDescription("Invalid data for the login, verify your login and password");
			return Response.status(Status.OK).entity(error.getJSON()).build();
		}
		
	}

}
