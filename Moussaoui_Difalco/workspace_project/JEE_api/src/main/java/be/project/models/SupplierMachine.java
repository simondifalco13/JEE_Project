package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.SupplierMachineDAO;
import be.project.enumerations.MachineType;

public class SupplierMachine extends Machine implements Serializable {


	private static final long serialVersionUID = -1458577908973852022L;
	private double price;
	private Supplier supplier;
	private ArrayList<Order> orders;
	public SupplierMachine() {
		// TODO Auto-generated constructor stub
	}

	public SupplierMachine(String model, String brand, String description, MachineType type,double price,Supplier supplier) {
		super(model, brand, description, type);
		this.price=price;
		this.setSupplier(supplier);
	}
	
	public SupplierMachine(String model, String brand, String description,
			MachineType type,double price,Supplier supplier,
			ArrayList<Order> orders) {
		this(model, brand, description, type,price,supplier);
		this.setOrders(orders);
	}
	
	public SupplierMachine(int id,String model, String brand, String description,
			MachineType type,double price,Supplier supplier,
			ArrayList<Order> orders) {
		this(model, brand, description, type,price,supplier,orders);
		this.setId(id);
	}

	//GET & SET
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public static ArrayList<SupplierMachine> getAllSuppliersMachines(String type) {
		ArrayList<SupplierMachine> machines=new ArrayList<SupplierMachine>();
		SupplierMachineDAO smDAO=new SupplierMachineDAO();
		machines=smDAO.findAllSuppliersMachines(type);
		return machines;
	}
	//METHODS

	public static SupplierMachine getSupplierMachine(int id) {
		SupplierMachine machine = new SupplierMachine();
		SupplierMachineDAO smDAO = new SupplierMachineDAO();
		machine= smDAO.find(id);
		return machine;
	}
	
}