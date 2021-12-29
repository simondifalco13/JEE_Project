package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.Date;

import be.project.enumerations.MachineType;
import be.project.enumerations.MaintenanceStatus;
import be.project.enumerations.OperationState;
import be.project.models.Area;
import be.project.models.FactoryMachine;
import be.project.models.Leader;
import be.project.models.Maintenance;
import be.project.models.Site;
import be.project.models.Worker;

public class MaintenanceDAO implements DAO<Maintenance> {

	@Override
	public boolean insert(Maintenance obj) {
		boolean success=false;
		int exception = -1;
		try {
			CallableStatement sql = conn.prepareCall("{call xx(?,?,?)}");
//			sql.setInt(1,obj.getId());
//			sql.setString(2,String.valueOf(obj.getOperationState()).toLowerCase());
//			sql.registerOutParameter(3, java.sql.Types.NUMERIC);
//			sql.executeUpdate();
//			exception = sql.getInt(3);
//			sql.close();
			success=true;
		}catch(SQLException e) {
			return false;
		}
		return success;
	}
	
	public int insertMaintenance(Maintenance obj) {
		int createdId=0;
		int exception = -1;
		try {
			CallableStatement sql = conn.prepareCall("{call xx(?,?,?)}");
			//creer procédure
//			sql.setInt(1,obj.getId());
//			sql.setString(2,String.valueOf(obj.getOperationState()).toLowerCase());
//			sql.registerOutParameter(3, java.sql.Types.NUMERIC);
//			sql.executeUpdate();
//			exception = sql.getInt(3);
//			sql.close();
		}catch(SQLException e) {
			return 0;
		}
		return createdId;
	}

	@Override
	public boolean delete(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Maintenance find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Maintenance> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ArrayList<Maintenance> getMachineMaintenances(int machineId){
		ArrayList<Maintenance> maintenances=new ArrayList<Maintenance>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT "
					+ "maintenance_id,maintenance_date,maintenance_status,"
					+ "leader_id,maintenance_start,maintenance_end "
					+ "FROM maintenance "
					+ "WHERE machine_id=?"
					);
			preparedStatement.setInt(1, machineId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int maintenanceId=resultSet.getInt("maintenance_id");
				Date maintenanceDate=resultSet.getDate("maintenance_date");
				MaintenanceStatus status=MaintenanceStatus.valueOf(resultSet.getString("maintenance_status"));
				int leaderId=resultSet.getInt("leader_id");
				Timestamp tsStart=resultSet.getTimestamp("maintenance_start");
				Timestamp tsEnd=resultSet.getTimestamp("maintenance_end");
				LocalTime start=null;
				LocalTime end=null;
				if(tsStart!=null) {
					start=tsStart.toLocalDateTime().toLocalTime();
				}if(tsEnd!=null) {
					end=tsEnd.toLocalDateTime().toLocalTime();
				}
				
				Leader leader=Leader.getLeader(leaderId);
				ArrayList<Worker> workers=Worker.getMaintenanceWorker(maintenanceId);
				FactoryMachine machine=new FactoryMachine();
				machine.setId(machineId);
				java.util.Date utilDate = new java.util.Date(maintenanceDate.getTime());
				Maintenance maintenance=new Maintenance(maintenanceId,utilDate,machine,status,workers,leader,start,end);
				maintenances.add(maintenance);
			}
			
			
		} catch (Exception e) {
			System.out.println("MAINTENANCE DAO : "+e.getMessage());
		}
		return maintenances;
		
	}

}
