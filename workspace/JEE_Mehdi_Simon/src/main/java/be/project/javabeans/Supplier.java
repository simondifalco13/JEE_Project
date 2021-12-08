package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Supplier implements Serializable {

	private static final long serialVersionUID = -1829190545470625389L;
	private int id;
	private String name;
	private String phone;
	private String email;
	private ArrayList<SupplierMachine> machines;
	
	public Supplier() {
		// TODO Auto-generated constructor stub
	}
	
	public Supplier(String supplierName,String supplierPhone,String supplierMail, ArrayList<SupplierMachine> machines) {
		this.name=supplierName;
		this.phone=supplierPhone;
		this.email=supplierMail;
		this.machines=machines;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<SupplierMachine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<SupplierMachine> machines) {
		this.machines = machines;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	//methods
	
	

}
