package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Leader extends User implements Serializable {

	private static final long serialVersionUID = 4637814402818196339L;
	private ArrayList<Maintenance> maintenances;

	public Leader() {
		// TODO Auto-generated constructor stub
	}

	public Leader(int serialNumber, String firstname, String lastname, String password, String email) {
		super(serialNumber, firstname, lastname, password, email);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Maintenance> getMaintenances() {
		return maintenances;
	}

	public void setMaintenances(ArrayList<Maintenance> maintenances) {
		this.maintenances = maintenances;
	}

}
