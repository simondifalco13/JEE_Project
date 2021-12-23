package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.project.enumerations.ColorCode;
import be.project.enumerations.MachineType;
import be.project.enumerations.OperationState;
import be.project.models.Area;
import be.project.models.FactoryMachine;
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
		int machineId,site_id,area_id;
		String siteCity,siteAddress,section;
		ColorCode dangerousness;
		MachineType type;
		OperationState status;
		ArrayList<Area> machineAreas=new  ArrayList<Area>();
		Site site=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT "
					+ "machine.machine_id,machine_type,machine.site_id,machine_status, "
					+ "site.city,site.address,"
					+ "areas.areas_id,section,dangerousness"
					+ "FROM machine "
					+ "LEFT JOIN site ON machine.site_id=site.site_id "
					+ "LEFT join machine_areas ON machine.machine_id=machine_areas.machine_id "
					+ "LEFT JOIN areas ON machine_areas.areas_id=areas.areas_id "
					+ "WHERE machine.site_id=? AND areas.site_id=machine.site_id"
					);
			preparedStatement.setInt(1, siteId);
			ResultSet resultSet=preparedStatement.executeQuery();
			int i=0;
			while(resultSet.next()) {
				if(i==0) {
					machineId=resultSet.getInt("machine.machine_id");
					type=MachineType.valueOf(resultSet.getString("machine_type"));
					site_id=resultSet.getInt("machine.site_id");
					status=OperationState.valueOf(resultSet.getString("machine_status"));
					siteCity=resultSet.getString("site.city");
					siteAddress=resultSet.getString("site.city");
					site=new Site(siteId,siteCity,siteAddress,null,null,null,null);
					i++;
				}
				area_id=resultSet.getInt("areas.areas_id");
				section=resultSet.getString("section");
				dangerousness=ColorCode.valueOf(resultSet.getString("dangerousness"));
				Area area=new Area(area_id,section,dangerousness,site);
				machineAreas.add(area);

			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return machines;
	}

}
