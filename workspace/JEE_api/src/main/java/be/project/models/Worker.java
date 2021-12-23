package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.WorkerDAO;
import be.project.models.Maintenance;
import be.project.models.Site;
import be.project.models.User;

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

	public static Worker getWorker(int serialNumber) {
		Worker worker=null;
		WorkerDAO workerDAO=new WorkerDAO();
		worker= workerDAO.find(serialNumber);
		return worker;
	}

}
