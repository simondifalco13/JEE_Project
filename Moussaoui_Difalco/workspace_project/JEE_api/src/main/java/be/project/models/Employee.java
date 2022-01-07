package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.EmployeeDAO;

public class Employee extends User implements Serializable {

	private static final long serialVersionUID = 4220445284917542794L;
	private ArrayList<Maintenance> allMaintenances;
	private ArrayList<Order> orders;
	
	

	public Employee() {
		
	}

	public Employee(int serialNumber, String firstname, String lastname, String password, String email,Site site) {
		super(serialNumber, firstname, lastname, password, email,site);
		
	}
	
	public Employee(int serialNumber, String firstname, String lastname, String password, String email,Site site,ArrayList<Maintenance> maintenances) {
		this(serialNumber, firstname, lastname, password, email,site);
		this.allMaintenances=maintenances;
		
	}
	
	public Employee(int serialNumber, String firstname, String lastname, String password, String email,Site site,ArrayList<Order> orders,ArrayList<Maintenance> maintenances) {
		this(serialNumber, firstname, lastname, password, email,site);
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
	public static Employee getEmployee(int serialNumber) {
		Employee emp=null;
		EmployeeDAO employeeDAO=new EmployeeDAO();
		emp=employeeDAO.find(serialNumber);
		return emp;
	}

}
