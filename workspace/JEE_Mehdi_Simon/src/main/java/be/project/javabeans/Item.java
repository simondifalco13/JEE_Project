package be.project.javabeans;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8124487508738093428L;
	private SupplierMachine machine;
	private double quantity;
	
	public Item() {
		// TODO Auto-generated constructor stub
	}
	
	public Item(SupplierMachine machine) {
		this.machine=machine;
	}
	
	public Item(SupplierMachine machine,double quantity) {
		this(machine);
		this.quantity=quantity;
	}

	public SupplierMachine getMachine() {
		return machine;
	}

	public void setMachine(SupplierMachine machine) {
		this.machine = machine;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public double getTotalPrice() {
		double price=0;
		price=(this.getMachine().getPrice())*(this.getQuantity());
		return price;
	}

	
}
