package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.project.enumerations.ColorCode;
import be.project.enumerations.MachineType;
import be.project.enumerations.OperationState;
import be.project.models.Area;
import be.project.models.FactoryMachine;
import be.project.models.Maintenance;
import be.project.models.Site;
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
	public boolean update(SupplierMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SupplierMachine find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SupplierMachine> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<SupplierMachine> findAllSuppliersMachines(String type) {
		
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
		return machines;
	}
}
