package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee extends User implements Serializable {

	private static final long serialVersionUID = 4220445284917542794L;
	private ArrayList<Maintenance> allMaintenances;

	public Employee() {
		
	}

	public Employee(int serialNumber, String firstname, String lastname, String password, String email) {
		super(serialNumber, firstname, lastname, password, email);
		
	}

	public ArrayList<Maintenance> getAllMaintenances() {
		return allMaintenances;
	}

	public void setAllMaintenances(ArrayList<Maintenance> allMaintenances) {
		this.allMaintenances = allMaintenances;
	}

}
