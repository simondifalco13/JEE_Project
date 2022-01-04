package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;

import be.project.dao.MaintenanceDAO;
import be.project.dao.WorkerDAO;

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

	
	public static ArrayList<Worker> findSiteWorker(Site site){
		ArrayList<Worker> workers=new ArrayList<Worker>();
		WorkerDAO workerDAO=new WorkerDAO();
		workers=workerDAO.findSiteWorker(site.getId());
		return workers;
	}


	public static ArrayList<Maintenance> getMaintenances(int workerId){
		ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
		maintenances = maintenanceDAO.getAllMaintenances(workerId);
		return maintenances;
	}

}
