package be.project.models;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.EmployeeDAO;
import be.project.dao.LeaderDAO;
import be.project.dao.WorkerDAO;
import be.project.models.Site;

public abstract class User  {

	
	private int serialNumber;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private Site site;
	
	public User() {
		
	}
	
	public User(int serialNumber,String firstname,String lastname,String password,String email,Site site) {
		this.serialNumber=serialNumber;
		this.firstname=firstname;
		this.lastname=lastname;
		this.password=password;
		this.email=email;
		this.site=site;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public static boolean login(int matricule,String pwd) {
		boolean success=false;
		if(matricule>=20000 && matricule<30000) {
			 WorkerDAO workerDAO=new WorkerDAO();
			 success=workerDAO.login(matricule, pwd);
		}
		if(matricule>=30000 && matricule<40000) {
			LeaderDAO leaderDAO=new LeaderDAO();
			success=leaderDAO.login(matricule, pwd);
		}
		if(matricule>=40000 && matricule<50000) {
			EmployeeDAO employeeDAO=new EmployeeDAO();
			success=employeeDAO.login(matricule,pwd);
		}
		
		return success;
		
	}

}
