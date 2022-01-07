package be.project.models;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;

import be.project.dao.SiteDAO;

public class Site implements Serializable {

	
	private static final long serialVersionUID = -2325842002428370742L;

	private int id;
	private String city;
	private String address;
	private ArrayList<Worker> siteWorkers;
	private ArrayList<Leader> siteLeaders;
	private ArrayList<Employee> siteEmployees;
	private ArrayList<Area> siteAreas;
	
	public Site() {
		// TODO Auto-generated constructor stub
	}
	
	

	
	public Site(String city,String address,ArrayList<Area> siteAreas,ArrayList<Leader> siteLeaders,ArrayList<Employee> siteEmployees,ArrayList<Worker> siteWorkers) {
		this.city=city;
		this.address=address;
		this.siteAreas=siteAreas;
		this.siteLeaders=siteLeaders;
		this.siteEmployees=siteEmployees;
		this.siteWorkers=siteWorkers;
	}
	
	public Site(int id,String city,String address,ArrayList<Area> siteAreas,ArrayList<Leader> siteLeaders,ArrayList<Employee> siteEmployees,ArrayList<Worker> siteWorkers) {
		this(city,address,siteAreas,siteLeaders,siteEmployees,siteWorkers);
		this.id=id;
	}
	
	
	public ArrayList<Worker> getSiteWorkers() {
		return siteWorkers;
	}

	public void setSiteWorkers(ArrayList<Worker> siteWorkers) {
		this.siteWorkers = siteWorkers;
	}

	public ArrayList<Leader> getSiteLeaders() {
		return siteLeaders;
	}

	public void setSiteLeaders(ArrayList<Leader> siteLeaders) {
		this.siteLeaders = siteLeaders;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public ArrayList<Area> getSiteAreas() {
		return siteAreas;
	}

	public void setSiteAreas(ArrayList<Area> siteAreas) {
		this.siteAreas = siteAreas;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Employee> getSiteEmployees() {
		return siteEmployees;
	}

	public void setSiteEmployees(ArrayList<Employee> siteEmployee) {
		this.siteEmployees = siteEmployee;
	}
	
	public static Site getSite(int id,Connection conn) {
		Site site=null;
		SiteDAO siteDAO=new SiteDAO();
		site=siteDAO.find(id,conn);
		return site;
	}
	
	
	

}
