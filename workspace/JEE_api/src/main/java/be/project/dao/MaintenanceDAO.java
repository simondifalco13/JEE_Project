package be.project.dao;

import java.sql.CallableStatement;
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

import be.project.models.Employee;
import be.project.models.Report;


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
			sql.setDate(2, new java.sql.Date(obj.getMaintenanceDate().getTime()));
			sql.setTimestamp(3,Timestamp.valueOf(start));
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

	public int update1(Maintenance obj) {
		int code =-1;
		CallableStatement callableStatement = null;
		try {
			String sql="{call changeStatusDone(?,?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setInt(1, obj.getMaintenanceId());
			callableStatement.setString(2, String.valueOf(obj.getStatus()));
			callableStatement.registerOutParameter(3, java.sql.Types.NUMERIC);
			callableStatement.executeUpdate();
			code= callableStatement.getInt(3);
			return code;
		}
		catch(SQLException e) {
			System.out.println("Erreur SQL update maintenanceDAO : " + e.getMessage() + e.toString() );
			return code;
		}
		finally {
			try {
				if(callableStatement!=null) {
					callableStatement.close();
				}	
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public Maintenance find(int id) {
		Maintenance maintenance=null;
		int count=0;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			preparedStatement = conn.prepareStatement(
				"SELECT DISTINCT maintenance.maintenance_id,maintenance.maintenance_date, maintenance.maintenance_status,"
				+"maintenance.maintenance_start,maintenance.maintenance_end,"
				+"maintenance.machine_id, maintenance.leader_id," 
				+"leader.leader_lastname,leader.leader_firstname," 
				+"worker_maintenance.report,worker.worker_id,worker.worker_firstname, worker.worker_lastname,"
				+"machine.machine_type, machine.model, machine.brand "
				+"FROM maintenance, worker_maintenance, leader, worker, machine "
				+"WHERE maintenance.maintenance_id = worker_maintenance.maintenance_id " 
				+"AND maintenance.leader_id = leader.leader_id "
				+"AND worker_maintenance.worker_id = worker.worker_id "
				+"AND machine.machine_id = maintenance.machine_id AND maintenance.maintenance_id=?"
				);
			preparedStatement.setInt(1, id);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				if(count == 0) {
					//int maintenanceId=resultSet.getInt("maintenance_id");
					Date maintenance_date = resultSet.getDate("maintenance_date");
					MaintenanceStatus status=MaintenanceStatus.valueOf(resultSet.getString("maintenance_status"));
					Timestamp timeStart = resultSet.getTimestamp("maintenance_start");
					Timestamp timeEnd = resultSet.getTimestamp("maintenance_end");
					LocalTime startTime = null;
					LocalTime endTime = null;
					if(timeStart != null) {
						startTime= timeStart.toLocalDateTime().toLocalTime();
					}
					if(timeEnd != null) {
						endTime= timeEnd.toLocalDateTime().toLocalTime();
					}
					int leaderId=resultSet.getInt("leader_id");
					Leader leader = new Leader();
					leader.setSerialNumber(leaderId);
					leader.setLastname(resultSet.getString("leader_lastname"));
					leader.setFirstname(resultSet.getString("leader_firstname"));
					
					FactoryMachine machine = new FactoryMachine();
					machine.setId(resultSet.getInt("machine_id"));
					machine.setType(MachineType.valueOf(resultSet.getString("machine_type")));
					machine.setModel(resultSet.getString("model"));
					machine.setBrand(resultSet.getString("brand"));	
					java.util.Date utilDate = new java.util.Date(maintenance_date.getTime());
					maintenance = new Maintenance(id,utilDate,startTime, endTime,status, machine, leader);
					count++;
				}
				Worker worker = new Worker();
				worker.setSerialNumber(resultSet.getInt("worker_id"));
				worker.setLastname(resultSet.getString("worker_lastname"));
				worker.setFirstname(resultSet.getString("worker_firstname"));
				maintenance.addWorker(worker);
				
				String reportString= resultSet.getString("report");
				Maintenance maintenanceTemp = new Maintenance();
				maintenanceTemp.setMaintenanceId(id);
				Report report = new Report(maintenanceTemp,worker,reportString);
				maintenance.addReport(report);
			}
		} catch (SQLException e) {
			System.out.println("Exception dans maintenanceDAO de l'api : "+ e.getMessage()+ e.toString());
		}
		finally {
			try {
				if(resultSet !=null) {
					resultSet.close();
				}
				if(preparedStatement!=null) {
					preparedStatement.close();
				}
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return maintenance;
	}

	@Override
	public ArrayList<Maintenance> findAll() {
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
			
			
		} catch (SQLException e) {
			System.out.println("MAINTENANCE DAO : "+e.getMessage());
		}
		return maintenances;
		
	}
	public ArrayList<Maintenance> getWorkerMaintenances(int id) {
		ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			 preparedStatement = conn.prepareStatement(
				"SELECT maintenance.maintenance_id,maintenance.maintenance_date, maintenance.maintenance_status,"
				+" maintenance.maintenance_start,maintenance.maintenance_end,"
				+" maintenance.machine_id, maintenance.leader_id, leader.leader_lastname, worker_maintenance.report "
				+" FROM maintenance, worker_maintenance, leader "
				+ "WHERE maintenance.maintenance_id = worker_maintenance.maintenance_id "
				+" AND maintenance.leader_id = leader.leader_id AND worker_id=?"
			);
			preparedStatement.setInt(1, id);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int maintenanceId=resultSet.getInt("maintenance_id");
				Date maintenance_date = resultSet.getDate("maintenance_date");
				MaintenanceStatus status=MaintenanceStatus.valueOf(resultSet.getString("maintenance_status"));
				Timestamp timeStart = resultSet.getTimestamp("maintenance_start");
				Timestamp timeEnd = resultSet.getTimestamp("maintenance_end");
				LocalTime startTime = null;
				LocalTime endTime = null;
				if(timeStart != null) {
					startTime= timeStart.toLocalDateTime().toLocalTime();
				}
				if(timeEnd != null) {
					endTime= timeEnd.toLocalDateTime().toLocalTime();
				}
				
				ArrayList<Report> reports = new ArrayList<Report>();
				ArrayList<Worker> workers = new ArrayList<Worker>();
				
				Worker worker = new Worker();
				worker.setSerialNumber(id);
				workers.add(worker);

				Maintenance maintenanceTemp = new Maintenance();
				maintenanceTemp.setMaintenanceId(maintenanceId);
				String reportString= resultSet.getString("report");
				Report report = new Report(maintenanceTemp,worker,reportString);
				reports.add(report);
				
				int machineId=resultSet.getInt("machine_id");
				FactoryMachine machine = new FactoryMachine();
				machine.setId(machineId);
				
				Leader leader = new Leader();
				int leaderId=resultSet.getInt("leader_id");
				leader.setLastname(resultSet.getString("leader_lastname"));
				leader.setSerialNumber(leaderId);
				Maintenance maintenance = new Maintenance(maintenanceId, maintenance_date, startTime, machine, status, workers,  leader, endTime,reports);
				maintenances.add(maintenance);
			}
		} catch (SQLException e) {
			System.out.println("Exception dans la worker DAO de l'api");
			System.out.println(e.getMessage() + e.toString());
		}
		finally {
			try {
				if(resultSet!=null) {
					resultSet.close();
				}
				if(preparedStatement!=null) {
					preparedStatement.close();
				}	
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return maintenances;
	}

	@Override
	public boolean update(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
