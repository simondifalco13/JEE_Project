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
import be.project.models.Worker;

public class FactoryMachineDAO implements DAO<FactoryMachine> {

	@Override
	public boolean insert(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FactoryMachine find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<FactoryMachine> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ArrayList<FactoryMachine> findAllSiteMachine(int siteId) {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		ArrayList<Maintenance> maintenances=new ArrayList<Maintenance>();
		FactoryMachine machine;
		int machineId=0,site_id,area_id;
		String siteCity,siteAddress,section,model,brand,description;
		ColorCode dangerousness;
		MachineType type;
		OperationState status;
		ArrayList<Area> machineAreas=new  ArrayList<Area>();
		Site site=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT "
					+ "machine_id,machine_type,site_id,machine_status,"
					+ "model,brand,description "
					+ "FROM machine "
					+ "WHERE site_id=?"
					);
			preparedStatement.setInt(1, siteId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				machineAreas=new  ArrayList<Area>();
				maintenances=new ArrayList<Maintenance>();
				machineId=resultSet.getInt("machine_id");
				type=MachineType.valueOf(resultSet.getString("machine_type"));
				site_id=resultSet.getInt("site_id");
				status=OperationState.valueOf(resultSet.getString("machine_status"));
				model=resultSet.getString("model");
				brand=resultSet.getString("brand");
				description=resultSet.getString("description");
				site=Site.getSite(site_id);
				if(machineId!=0) {
					machineAreas=Area.getMachineAreas(machineId);
					maintenances=Maintenance.getMachineMaintenances(machineId);
				}
				machine=new FactoryMachine(machineId,model,brand,description,type,status,machineAreas,maintenances);
				machines.add(machine);
			}
			
			
		} catch (Exception e) {
			System.out.println("FACTORYMACHINEDAO :"+e.getMessage());
		}
		return machines;
	}

}
