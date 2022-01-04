package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import be.project.models.SupplierMachine;
import be.project.dao.OrderDAO;
import be.project.models.Employee;
import be.project.models.Item;

public class Order implements Serializable {


	private static final long serialVersionUID = -8787806183257791080L;
	private ArrayList<Item> orderItems;
	private int orderNumber;
	private Employee employee;
	private double totalPrice;
	private Date orderDate;
	
	public Order() {
		
	}
	
	public Order(Employee employee,ArrayList<Item> orderItems)
	{
		this.employee=employee;
		this.orderItems=orderItems;
		this.totalPrice=calculateTotalPrice(this.orderItems);
	}
	
	

	public Order(int orderNumber, Date date,Employee employee, double totalPrice) {
		this.employee = employee;
		this.orderNumber=orderNumber;
		this.orderDate=date;
		this.totalPrice = totalPrice;
		this.orderItems=new ArrayList<Item>();
	}

	//GET & SET
	public ArrayList<Item> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(ArrayList<Item> orderItems) {
		this.orderItems = orderItems;
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

	

	
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	//METHODS
	public void addAmountToTotal(double amount) {
		double total=this.getTotalPrice();
		total+=amount;
		this.setTotalPrice(total);
	}
	
	public double calculateTotalPrice(ArrayList<Item> orderItems) {
		double price=0;
		for(int i=0;i<orderItems.size();i++) {
			price+=orderItems.get(i).getTotalPrice();
		}
		return price;
	}
	public void addItem(Item item) {
		if(this.getOrderItems()==null) {
			this.orderItems = new ArrayList<Item>();
		}
		orderItems.add(item);
	}
	
	public void addItemAndPrice(Item item) {
		if(this.getOrderItems()==null) {
			this.orderItems = new ArrayList<Item>();
		}
		orderItems.add(item);
		double itemPrice=item.getTotalPrice();
		this.addAmountToTotal(itemPrice);
	}
	
	public static ArrayList<Order> getAllOrders()
	{
		ArrayList<Order> orders=new ArrayList<Order>();
		OrderDAO orderDAO=new OrderDAO();
		orders=orderDAO.findAll();
		return orders;
		
	}

	public int insertOrder() {
		OrderDAO orderDAO = new OrderDAO();
		return orderDAO.insertOrder(this);
	}

}

