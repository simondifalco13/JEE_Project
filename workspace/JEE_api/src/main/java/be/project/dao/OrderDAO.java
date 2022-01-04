package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import be.project.enumerations.MachineType;
import be.project.models.Area;
import be.project.models.Employee;
import be.project.models.FactoryMachine;
import be.project.models.Item;
import be.project.models.Maintenance;
import be.project.models.Order;
import be.project.models.Site;
import be.project.models.Supplier;
import be.project.models.SupplierMachine;
import be.project.models.Worker;

public class OrderDAO implements DAO<Order> {

	@Override
	public boolean insert(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int insertOrder(Order obj) {
		int createdId=0;
		int code=0;
		try {
			CallableStatement sql = conn.prepareCall("{call insert_orders(?,?,?,?,?,?)}");
			Date date = new Date();  
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			sql.setDate(1,sqlDate);
			sql.setDouble(2, obj.getTotalPrice());
			sql.setInt(3, obj.getEmployee().getSerialNumber());
			sql.setInt(5, obj.getOrderItems().get(0).getMachine().getId());
			sql.registerOutParameter(4, java.sql.Types.NUMERIC);
			sql.registerOutParameter(6, java.sql.Types.NUMERIC);
			sql.executeUpdate();
			createdId = sql.getInt(4);
			code=sql.getInt(6);
			if(code != 0) {
				return -1;
			}
		}catch(SQLException e) {
			System.out.println("Exception dans OrderDao de l'api " + e.getMessage());
			return code;
		}
		return createdId;
	}
	@Override
	public boolean delete(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Order obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Order find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Order> findAll() {
		ArrayList<Order> orders=new ArrayList<Order>();
		int orderNumber=0;
		Order order=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT "
					+ "orders.order_id AS ord_id,"
					+ "order_date,"
					+ "orders.employee_id AS emp_id, employee_firstname,employee_lastname,"
					+ "order_supplier_machine.machine_id AS mach_id, "
					+ "model,brand,supplier_machine.price AS machine_price, "
					+ "description,machine_type,supplier_machine.supplier_id as suppl_id,"
					+ "supplier_name,"
					+ "quantity,orders.price AS price_order FROM orders "
					+ "LEFT JOIN order_supplier_machine "
					+ "ON orders.order_id=order_supplier_machine.order_id "
					+ "LEFT JOIN supplier_machine "
					+ "ON supplier_machine.supplier_machine_id=order_supplier_machine.machine_id "
					+ "LEFT JOIN factory_employee ON factory_employee.employee_id=orders.employee_id "
					+ "LEFT JOIN supplier ON supplier_machine.supplier_id=supplier.supplier_id"
					);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {

				orderNumber=resultSet.getInt("ord_id");
				Date orderDate = resultSet.getDate("order_date");
				int employeeId=resultSet.getInt("emp_id");
				int orderPrice=resultSet.getInt("price_order");
				String empFirstname=resultSet.getString("employee_firstname");
				String empLastname=resultSet.getString("employee_lastname");
				Employee emp=new Employee();
				emp.setSerialNumber(employeeId);
				emp.setLastname(empLastname);
				emp.setFirstname(empFirstname);
				order=new Order(orderNumber,orderDate,emp,orderPrice);
				order.setTotalPrice(resultSet.getDouble("price_order"));
				

				int machineId=resultSet.getInt("mach_id");
				String model=resultSet.getString("model");
				String brand=resultSet.getString("brand");
				MachineType type=MachineType.valueOf(resultSet.getString("machine_type"));
				Double price=resultSet.getDouble("machine_price");
				String description=resultSet.getString("description");
				int supplier_id=resultSet.getInt("suppl_id");
				String supplierName=resultSet.getString("supplier_name");
				Supplier supplier=new Supplier();
				supplier.setId(supplier_id);
				supplier.setName(supplierName);
				int quantity=resultSet.getInt("quantity");
				SupplierMachine machine=new SupplierMachine(machineId,model,brand,
						description,type,price,supplier,null);
				Item item=new Item(machine,quantity);
				order.addItem(item);
				orders.add(order);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("ORDERDAO API :"+e.getMessage());
		}
		return orders;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
