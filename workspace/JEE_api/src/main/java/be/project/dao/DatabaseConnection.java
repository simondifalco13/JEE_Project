package be.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;

public class DatabaseConnection {

	public Connection connection;
	private static Connection instance = null;

	private DatabaseConnection(){
		
		try{ 
			Context ctx = new InitialContext();
		    Context env = (Context) ctx.lookup("java:comp/env");
		    final String connectionString = (String) env.lookup("connectionString");
		    final String username = (String) env.lookup("dbUser");
		    final String pwd = (String) env.lookup("dbUserPwd");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			instance=DriverManager.getConnection(
					connectionString,
					username,
					pwd);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static Connection getInstance() { 
		if(instance == null){
			new DatabaseConnection();
		}
		return instance;
	}

}
