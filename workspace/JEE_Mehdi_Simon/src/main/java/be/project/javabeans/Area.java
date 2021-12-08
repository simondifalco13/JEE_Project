package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.enumerations.ColorCode;

public class Area implements Serializable {

	private static final long serialVersionUID = 2310261736651555419L;

	private int id;
	private String section;
	private ColorCode dangerousness;
	private Site areaSite;
	private ArrayList<FactoryMachine> areaMachines;
	
	public Area() {
		// TODO Auto-generated constructor stub
	}
	
	public Area(String section, ColorCode dangerousness,Site areaSite) {
		this.section=section;
		this.dangerousness=dangerousness;
		this.areaSite=areaSite;
	}
	public Area(String section, ColorCode dangerousness,Site areaSite,ArrayList<FactoryMachine> machines) {
		this(section,dangerousness,areaSite);
		this.areaMachines=machines;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public ColorCode getDangerousness() {
		return dangerousness;
	}

	public void setDangerousness(ColorCode dangerousness) {
		this.dangerousness = dangerousness;
	}

	public Site getAreaSite() {
		return areaSite;
	}

	public void setAreaSite(Site areaSite) {
		this.areaSite = areaSite;
	}

	public ArrayList<FactoryMachine> getAreaMachines() {
		return areaMachines;
	}

	public void setAreaMachines(ArrayList<FactoryMachine> areaMachines) {
		this.areaMachines = areaMachines;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

}
