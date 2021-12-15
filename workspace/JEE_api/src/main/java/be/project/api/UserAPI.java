package be.project.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import be.project.models.User;

@Path("/user")
public class UserAPI {

	public UserAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@FormParam("matricule") int matricule, 
			@FormParam("pwd") String password) {
	
		return User.login(matricule, password);
		
	}

}
