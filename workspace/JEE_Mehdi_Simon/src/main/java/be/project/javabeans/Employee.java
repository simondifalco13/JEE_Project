package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee extends User implements Serializable {

	private static final long serialVersionUID = 4220445284917542794L;
	private ArrayList<Maintenance> allMaintenances;
	private ArrayList<Order> orders;
	

	public Employee() {
		
	}

	public Employee(int serialNumber, String firstname, String lastname, String password, String email) {
		super(serialNumber, firstname, lastname, password, email);
		
	}
	
	public Employee(int serialNumber, String firstname, String lastname, String password, String email,ArrayList<Maintenance> maintenances) {
		this(serialNumber, firstname, lastname, password, email);
		this.allMaintenances=maintenances;
		
	}
	
	public Employee(int serialNumber, String firstname, String lastname, String password, String email,ArrayList<Order> orders,ArrayList<Maintenance> maintenances) {
		this(serialNumber, firstname, lastname, password, email);
		this.setOrders(orders);
		this.allMaintenances=maintenances;
	}
	
	
	
	
	
	//GET & SET
	public ArrayList<Maintenance> getAllMaintenances() {
		return allMaintenances;
	}

	public void setAllMaintenances(ArrayList<Maintenance> allMaintenances) {
		this.allMaintenances = allMaintenances;
	}
	
	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	
	
	//METHODS
	public void addOrder(Order order) {
		if(order!=null) {
			this.getOrders().add(order);
		}
	}

}
