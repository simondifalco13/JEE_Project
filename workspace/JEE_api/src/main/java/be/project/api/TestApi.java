package be.project.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.enumerations.ColorCode;
import be.project.models.Area;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Path("/test")
public class TestApi {

	public Connection connection;

	public TestApi() {
		try {
		    connection=DatabaseConnection.getInstance();    
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClient(@PathParam("id") int id) {
		String name="";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT ename FROM emp WHERE empno=?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				 name=resultSet.getString("ename").toLowerCase();
			}
			String content="{\"name\":"+"\""+name+"\""+"}";
			return Response.status(Status.OK).entity(content).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			
		}
		
	}
}
