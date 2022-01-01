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
		WorkerDAO workerDAO = new WorkerDAO();
		maintenances = workerDAO.getAllMaintenances(this);
		return maintenances;
	}

	public void setMaintenances(ArrayList<Maintenance> maintenances) {
		this.maintenances = maintenances;
	}
	public static ArrayList<Maintenance> getMaintenances(Worker worker){
		ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
		WorkerDAO workerDAO = new WorkerDAO();
		maintenances = workerDAO.getAllMaintenances(worker);
		return maintenances;
	}
}
