package be.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {


	String co="jdbc:oracle:thin:@//193.190.64.10:1522/XEPDB1";
	String us="student03_06";
	String pwd="root";
	public Connection connection;
	private static Connection instance = null;

	private DatabaseConnection(){
		try{ 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			instance=DriverManager.getConnection(
					co,
					us,
					pwd);
		}
		catch (Exception ex) {
			//gestion d'erreur
		}
		
	}
	
	public static Connection getInstance() { 
		if(instance == null){
			new DatabaseConnection();
		}
		return instance;
	}

}
