package be.project.javabeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {

	
	private static final long serialVersionUID = -8787806183257791080L;
	private int id;
	private Map<SupplierMachine,Double> orderMachines;
	private int orderNumber;
	private Employee employee;
	private double totalPrice;
	
	public Order() {
		
	}
	
	public Order(Employee employee,Map<SupplierMachine,Double> orderMachines)
	{
		this.employee=employee;
		this.orderMachines=orderMachines;
		this.totalPrice=calculateTotalPrice(this.orderMachines);
	}

	//GET & SET
	public Map<SupplierMachine, Double> getOrderMachines() {
		return orderMachines;
	}

	public void setOrderMachines(Map<SupplierMachine, Double> orderMachines) {
		this.orderMachines = orderMachines;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//METHODS
	public void addAmountToTotal(double amount) {
		double total=this.getTotalPrice();
		total+=amount;
		this.setTotalPrice(total);
	}
	
	public double calculateTotalPrice(Map<SupplierMachine, Double> orderMachines) {
		double price=0;
		for(SupplierMachine machine : orderMachines.keySet()) {
			double quantity=orderMachines.get(machine);
			price+=(quantity*machine.getPrice());
		}
		return price;
	}
	public void addMachine(SupplierMachine machine,double quantity) {
		orderMachines.put(machine,quantity);
		double machinePrice=machine.getPrice();
		this.addAmountToTotal(machinePrice);
	}

}
