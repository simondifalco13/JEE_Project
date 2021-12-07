package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Leader extends User implements Serializable {

	private static final long serialVersionUID = 4637814402818196339L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private ArrayList<Maintenance> maintenances;
	private Site site;
	private ArrayList<Control> controls;

	public Leader() {
		// TODO Auto-generated constructor stub
	}

	public Leader(int serialNumber, String firstname, String lastname, String password, String email,Site site) {
		super(serialNumber, firstname, lastname, password, email);
		this.setSite(site);
	}
	
	
	
	public Leader(int serialNumber, 
			String firstname, String lastname, 
			String password, String email,Site site,
			ArrayList<Maintenance> maintenances) {
		this(serialNumber, firstname, lastname, password, email,site);
		this.maintenances=maintenances;
	}

	//GET & SET
	public ArrayList<Maintenance> getMaintenances() {
		return maintenances;
	}

	public void setMaintenances(ArrayList<Maintenance> maintenances) {
		this.maintenances = maintenances;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public ArrayList<Control> getControls() {
		return controls;
	}

	public void setControls(ArrayList<Control> controls) {
		this.controls = controls;
	}
	
	//METHODS
	public void addControl(Control control) {
		if(control!=null)
			this.getControls().add(control);
	}

}
