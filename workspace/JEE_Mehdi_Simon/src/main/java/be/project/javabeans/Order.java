package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.project.dao.OrderDAO;
import be.project.javabeans.Item;

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
		if(orderItems==null) {
			orderItems=new ArrayList<Item>();
		}
		orderItems.add(item);
		
	}
	
	public void addItemWithPrice(Item item) {
		if(this.getOrderItems()==null) {
			this.orderItems = new ArrayList<Item>();
		}
		orderItems.add(item);
		double itemPrice=item.getTotalPrice();
		this.addAmountToTotal(itemPrice);
	}
	
	public static ArrayList<Order> getAllOrders(){
		ArrayList<Order> orders=new ArrayList<Order>();
		OrderDAO orderDAO=new OrderDAO();
		orders=orderDAO.findAll();
		return orders;
	}
	
	public static ArrayList<Order> getOrderByJSONArray(JSONArray jsonArray){
		ArrayList<Order> orders=new ArrayList<Order>();
		ObjectMapper mapper=new ObjectMapper();
		try {
			for(int i=0; i<jsonArray.length();i++) {
				JSONObject currentObject=(JSONObject) jsonArray.get(i);
				Order order=new Order();
				order.setOrderNumber(currentObject.getInt("orderNumber"));
				order.setTotalPrice(currentObject.getDouble("totalPrice"));
				order.setOrderDate(new Date((long)currentObject.get("orderDate")));
				Employee emp=(Employee) mapper.readValue(currentObject.get("employee").toString(),Employee.class);
				order.setEmployee(emp);
				mapper=new ObjectMapper();
			
				JSONArray arrayItems=(JSONArray) currentObject.getJSONArray("orderItems");
				for(int j=0;j<arrayItems.length();j++) {
					JSONObject currentItem=(JSONObject) arrayItems.get(j);
					SupplierMachine machine = (SupplierMachine) mapper.readValue(currentItem.get("machine").toString(), SupplierMachine.class);
					int quantity=currentItem.getInt("quantity");
					Item item=new Item(machine,quantity);
					order.addItem(item);
				}
				orders.add(order);
			}
		
		} catch (Exception e) {
			System.out.println("Probl�me conversion json en objet maintenance dans ORDERDAO : " + e.getMessage());
			return null;
		}
		return orders;
	}


	public boolean insertOrder() {
		OrderDAO orderDAO = new OrderDAO();
		return orderDAO.insert(this);
	}

}
