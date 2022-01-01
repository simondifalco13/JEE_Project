package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import be.project.utils.Error;

public class MaintenanceDAO implements DAO<Maintenance> {

	@Override
	public boolean insert(Maintenance obj) {
		boolean success=false;
		int exception = -1;
		try {
			CallableStatement sql = conn.prepareCall("{call xx(?,?,?)}");

			success=true;
		}catch(SQLException e) {
			return false;
		}
		return success;
	}
	
	public int insertMaintenance(Maintenance obj) {
		int createdId=0;
		int exception = -1;
		LocalDate maintenanceDate=obj.getMaintenanceDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDateTime start= LocalDateTime.of(maintenanceDate, obj.getStartTime());
		try {
			CallableStatement sql = conn.prepareCall("{call insert_maintenance(?,?,?,?,?,?,?,?)}");
			sql.setDate(1, new java.sql.Date(obj.getMaintenanceDate().getTime()));
			sql.setString(2, obj.getStatus().toString());
			sql.setInt(3, obj.getMachine().getId());
			sql.setInt(4,obj.getMaintenanceLeader().getSerialNumber());
			sql.setTimestamp(5,Timestamp.valueOf(start));
			sql.setTimestamp(6,null);
			sql.registerOutParameter(7, java.sql.Types.NUMERIC);
			sql.registerOutParameter(8, java.sql.Types.NUMERIC);
			sql.executeUpdate();
			createdId = sql.getInt(7);
			exception=sql.getInt(8);
			sql.close();
			if(exception!=0) {
				return 0;
			}else {
				if(createdId!=0) {
					for(Worker worker : obj.getMaintenanceWorkers()) {
						CallableStatement sql2 = conn.prepareCall("{call insert_worker_maintenance(?,?,?,?)}");
						sql2.setInt(1, worker.getSerialNumber());
						sql2.setInt(2,createdId);
						sql2.setString(3,null);
						sql2.registerOutParameter(4, java.sql.Types.NUMERIC);
						sql2.executeUpdate();
						exception=sql2.getInt(4);
						sql2.close();
					}
					return createdId;
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
		return createdId;
	}

	@Override
	public boolean delete(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	public int updateMaintenance(Maintenance obj) {
		int exception = -1;
		LocalDate maintenanceDate=obj.getMaintenanceDate()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDateTime start= LocalDateTime.of(maintenanceDate, obj.getStartTime());
		LocalDateTime end=null;
		if(obj.getEndTime()!=null) {
			end=LocalDateTime.of(maintenanceDate, obj.getEndTime());
		}
		try {
			CallableStatement sql = conn.prepareCall("{call update_maintenance(?,?,?,?,?)}");
			sql.setInt(1, obj.getMaintenanceId());
			sql.setTimestamp(2,Timestamp.valueOf(start));
			sql.setTimestamp(3,Timestamp.valueOf(end));
			sql.setString(4,obj.getStatus().toString());
			sql.registerOutParameter(5, java.sql.Types.NUMERIC);
			sql.executeUpdate();
			exception=sql.getInt(5);
			sql.close();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return Error.SQL_EXCEPTION.getCode();
		}
		return exception;
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

	@Override
	public boolean update(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
