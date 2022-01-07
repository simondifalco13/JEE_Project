package be.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import be.project.enumerations.ColorCode;
import be.project.enumerations.MachineType;
import be.project.enumerations.MaintenanceStatus;
import be.project.enumerations.OperationState;
import be.project.models.Area;
import be.project.models.FactoryMachine;
import be.project.models.Leader;
import be.project.models.Maintenance;
import be.project.models.Report;
import be.project.models.Site;
import be.project.models.Worker;
import be.project.utils.Error;
import oracle.jdbc.OracleTypes;

import javax.ws.rs.core.Response.Status;

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
	public int update(FactoryMachine obj)  {
		int success=1;
		int exception = -1;
		Connection conn=DatabaseConnection.getConnection();
		try {
			CallableStatement sql = conn.prepareCall("{call update_machine(?,?,?)}");
			sql.setInt(1,obj.getId());
			sql.setString(2,String.valueOf(obj.getOperationState()).toLowerCase());
			sql.registerOutParameter(3, java.sql.Types.NUMERIC);
			sql.executeUpdate();
			exception = sql.getInt(3);
			sql.close();
			success=1;
		}catch(SQLException e) {
			Error erreur = Error.SQL_EXCEPTION;
			return erreur.getCode();

		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return success;
	}

	@Override
	public FactoryMachine find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<FactoryMachine> findAll() {
		CallableStatement callableStatement = null;
		ResultSet resultSet=null;
		Connection conn=DatabaseConnection.getConnection();
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
			String sql="{call selectAllFactoryMachines(?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			callableStatement.execute();
			
			
			
			resultSet = (ResultSet) callableStatement.getObject(1);
		
			while(resultSet.next()){
				machineAreas=new  ArrayList<Area>();
				maintenances=new ArrayList<Maintenance>();
				machineId=resultSet.getInt("machine_id");
				type=MachineType.valueOf(resultSet.getString("machine_type"));
				site_id=resultSet.getInt("site_id");
				status=FactoryMachine.getOperationStateFromString(resultSet.getString("machine_status"));
				model=resultSet.getString("model");
				brand=resultSet.getString("brand");
				description=resultSet.getString("description");

				site=Site.getSite(site_id,conn);

				if(machineId!=0) {
					machineAreas=Area.getMachineAreas(machineId,conn);
				
					maintenances=Maintenance.getMachineMaintenances(machineId,conn);
					}
				machine=new FactoryMachine(machineId,model,brand,description,type,status,machineAreas,maintenances);
				machines.add(machine);
				
				}
			
		} catch (Exception e) {
			System.out.println("FACTORYMACHINEDAO FIND ALL:"+e.getMessage());
		}
		finally {
			try {
				if(resultSet !=null) {
					resultSet.close();
				}
				if(callableStatement!=null) {
					callableStatement.close();
				}
				if(conn!=null) {
					conn.close();
				}

				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return machines;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ArrayList<FactoryMachine> findAllSiteMachine(int siteId) {
		CallableStatement callableStatement = null;
		ResultSet resultSet=null;
		Connection conn=DatabaseConnection.getConnection();
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
			String sql="{call selectAllSiteMachine(?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setInt(1, siteId);
			callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
			callableStatement.execute();
		
			resultSet = (ResultSet) callableStatement.getObject(2);
		
			while(resultSet.next()){
				machineAreas=new  ArrayList<Area>();
				maintenances=new ArrayList<Maintenance>();
				machineId=resultSet.getInt("machine_id");
				type=MachineType.valueOf(resultSet.getString("machine_type"));
				site_id=resultSet.getInt("site_id");
				status=FactoryMachine.getOperationStateFromString(resultSet.getString("machine_status"));
				model=resultSet.getString("model");
				brand=resultSet.getString("brand");
				description=resultSet.getString("description");
				site=Site.getSite(site_id,conn);
				if(machineId!=0) {
					machineAreas=Area.getMachineAreas(machineId,conn);
					maintenances=Maintenance.getMachineMaintenances(machineId,conn);
				}
				machine=new FactoryMachine(machineId,model,brand,description,type,status,machineAreas,maintenances);
				machines.add(machine);
			}
			
		} catch (Exception e) {
			System.out.println("FACTORYMACHINEDAO :"+e.getMessage());
		}
		finally {
			try {
				if(resultSet !=null) {
					resultSet.close();
				}
				if(callableStatement!=null) {
					callableStatement.close();
				}
				if(conn!=null) {
					conn.close();
				}
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return machines;
	}
}
