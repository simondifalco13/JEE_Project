package be.project.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import be.project.enumerations.MachineType;
import be.project.enumerations.MaintenanceStatus;
import be.project.models.Employee;
import be.project.models.FactoryMachine;
import be.project.models.Leader;
import be.project.models.Maintenance;
import be.project.models.Report;
import be.project.models.Site;
import be.project.models.Worker;

public class MaintenanceDAO implements DAO<Maintenance> {

	@Override
	public boolean insert(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Maintenance obj) {
		return false;
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
			ResultSet resultSet=preparedStatement.executeQuery();
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
					
					maintenance = new Maintenance(id,maintenance_date,startTime, endTime,status, machine, leader);
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
}
