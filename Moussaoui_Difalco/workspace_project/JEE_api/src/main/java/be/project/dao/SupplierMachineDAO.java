package be.project.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.enumerations.MachineType;
import be.project.models.Supplier;
import be.project.models.SupplierMachine;

public class SupplierMachineDAO implements DAO<SupplierMachine>  {

	@Override
	public boolean insert(SupplierMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(SupplierMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(SupplierMachine obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SupplierMachine find(int id) {
		Connection conn=DatabaseConnection.getConnection();
		SupplierMachine machine=new SupplierMachine();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			preparedStatement = conn.prepareStatement(
					"select sm.model,sm.brand,sm.price,sm.machine_type,sm.description,sm.supplier_id, s.supplier_name "
					+ "FROM supplier_machine sm, supplier s WHERE sm.supplier_id=s.supplier_id AND supplier_machine_id=?"
				);
			preparedStatement.setInt(1, id);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				machine.setId(id);
				machine.setModel(resultSet.getString("model"));
				machine.setBrand(resultSet.getString("brand"));
				machine.setPrice(resultSet.getDouble("price"));
				machine.setType(MachineType.valueOf(resultSet.getString("machine_type")));
				if(resultSet.getString("description")!=null) {
					machine.setDescription(resultSet.getString("description"));
				}else { machine.setDescription("");}
				machine.setSupplier(null);
				Supplier supplier = new Supplier();
				supplier.setId(resultSet.getInt("supplier_id"));
				supplier.setName(resultSet.getString("supplier_name"));
				machine.setSupplier(supplier);
			}
		} catch (SQLException e) {
			System.out.println("Exception dans SupplierDAO de l'api : "+ e.getMessage()+ e.toString());
		}
		finally {
			try {
				if(resultSet !=null) {
					resultSet.close();
				}
				if(preparedStatement!=null) {
					preparedStatement.close();
				}
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return machine;
	}

	@Override
	public ArrayList<SupplierMachine> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<SupplierMachine> findAllSuppliersMachines(String type) {
		Connection conn=DatabaseConnection.getConnection();
		ArrayList<SupplierMachine> machines=new ArrayList<SupplierMachine>();
		SupplierMachine machine;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"select supplier_machine.supplier_machine_id, supplier_machine.model,"
				+"supplier_machine.brand, supplier_machine.price,"
				+"supplier_machine.description, supplier.supplier_name, supplier.supplier_id "
				+"FROM supplier, supplier_machine "
				+"WHERE supplier.supplier_id=supplier_machine.supplier_id "
				+"AND supplier_machine.machine_type=?"
				);
			preparedStatement.setString(1, type);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				machine = new SupplierMachine();
				machine.setId(resultSet.getInt("supplier_machine_id"));
				machine.setModel(resultSet.getString("model"));
				machine.setBrand(resultSet.getString("brand"));
				machine.setPrice(resultSet.getDouble("price"));
				machine.setType(MachineType.valueOf(type));
				machine.setDescription(resultSet.getString("description"));
				Supplier supplier = new Supplier();
				supplier.setId(resultSet.getInt("supplier_id"));
				supplier.setName(resultSet.getString("supplier_name"));
				machine.setSupplier(supplier);
				
				machines.add(machine);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans suppleirMachineDAO de l'api :"+e.getMessage());
		}
		finally {
			try {
				
				conn.close();
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return machines;
	}
}
