package be.project.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import be.project.models.Order;
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
	public boolean update(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
