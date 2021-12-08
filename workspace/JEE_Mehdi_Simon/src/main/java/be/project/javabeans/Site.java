package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Site implements Serializable {

	
	private static final long serialVersionUID = -2325842002428370742L;

	private int id;
	private String city;
	private String address;
	private ArrayList<Worker> siteWorkers;
	private ArrayList<Leader> siteLeaders;
	private ArrayList<Area> siteAreas;
	
	public Site() {
		// TODO Auto-generated constructor stub
	}
	
	public Site(String city,String address,ArrayList<Area> siteAreas,ArrayList<Leader> siteLeaders) {
		this.city=city;
		this.address=address;
		this.setSiteAreas(siteAreas);
		this.siteLeaders=siteLeaders;
	}
	
	public Site(String city,String address,ArrayList<Area> siteAreas,ArrayList<Leader> siteLeaders,ArrayList<Worker> siteWorkers) {
		this(city,address,siteAreas,siteLeaders);
		this.siteWorkers=siteWorkers;
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
	

}
