package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.LeaderDAO;
import be.project.models.Maintenance;
import be.project.models.Site;
import be.project.models.User;

public class Leader extends User implements Serializable {

	private static final long serialVersionUID = 4637814402818196339L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private ArrayList<Maintenance> maintenances;
	

	public Leader() {
		// TODO Auto-generated constructor stub
	}

	public Leader(int serialNumber, String firstname, String lastname, String password, String email,Site site) {
		super(serialNumber, firstname, lastname, password, email,site);
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

	public static Leader getLeader(int serialNumber) {
		Leader leader=null;
		LeaderDAO leaderDAO=new LeaderDAO();
		leader=leaderDAO.find(serialNumber);
		return leader;
	}
	

}