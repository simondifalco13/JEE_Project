package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Worker extends User implements Serializable {

	private static final long serialVersionUID = -7383376761861387496L;
	
	private ArrayList<Maintenance> maintenances;
	

	public Worker() {
		
	}

	public Worker(int serialNumber, String firstname, String lastname, String password, String email,Site site) {
		super(serialNumber, firstname, lastname, password, email,site);
		

	}
	
	public Worker(int serialNumber, String firstname, 
			String lastname, String password, 
			String email,Site site,
			ArrayList<Maintenance> maintenances) {
		this(serialNumber, firstname, lastname, password, email,site);
		this.maintenances=maintenances;

	}
	
	
	
	public ArrayList<Maintenance> getMaintenances() {
		return maintenances;
	}

	public void setMaintenances(ArrayList<Maintenance> maintenances) {
		this.maintenances = maintenances;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
