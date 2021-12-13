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

@Path("/test")
public class TestApi {

	String co="jdbc:oracle:thin:@//193.190.64.10:1522/XEPDB1";
	String us="student03_06";
	String pwd="root";
	public Connection connection;
	
	
	
	public TestApi() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection=DriverManager.getConnection(
					co,
					us,
					pwd);
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
