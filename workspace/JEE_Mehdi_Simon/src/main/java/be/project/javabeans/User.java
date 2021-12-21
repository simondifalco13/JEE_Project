package be.project.javabeans;

import java.io.Serializable;

import be.project.dao.UserDAO;

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
	
	public static boolean login(int serialNumber,String pwd) {
		boolean success=false;
		UserDAO userDAO;
		if(serialNumber!=0 && !pwd.isEmpty() && !pwd.equals("")) {
			userDAO=new UserDAO();
			success=userDAO.login(serialNumber, pwd);
		}else {
			success=false;
		}
		return success;
	}
	
	public static User getUser(int serialNumber) {
		User user=null;
		UserDAO userDAO=new UserDAO();
		user=userDAO.find(serialNumber);
		return user;
	}

}
