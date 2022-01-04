package be.project.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.Maintenance;
import be.project.models.Report;
import be.project.models.Worker;

public class ReportDAO implements DAO<Report> {

	@Override
	public int update(Report obj) {
		return 0;
	}
	public int update1(Report obj) {
		int code =-1;
		CallableStatement callableStatement = null;
		try {
			String sql="{call createReport(?,?,?,?)}";
			callableStatement = conn.prepareCall(sql);
			callableStatement.setInt(1, obj.getMaintenance().getMaintenanceId());
			callableStatement.setInt(2, obj.getWorker().getSerialNumber());
			callableStatement.setString(3, obj.getReport());
			callableStatement.registerOutParameter(4, java.sql.Types.NUMERIC);
			callableStatement.executeUpdate();
			code= callableStatement.getInt(4);
			return code;
		}
		catch(SQLException e) {
			System.out.println("Erreur SQL update reportDAO " + e.getMessage() + e.toString() );
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
	public boolean delete(Report obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(Report obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Report find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Report> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Report> getMaintenanceReports(int maintenanceId){
		 ArrayList<Report> reports=new  ArrayList<Report>();
		 try {
				PreparedStatement preparedStatement = conn.prepareStatement(
						"SELECT "
						+ "worker_maintenance.worker_id as w_id,"
						+ "worker_firstname,worker_lastname,"
						+ "worker_mail,report "
						+ "FROM worker_maintenance "
						+ "INNER JOIN worker "
						+ "ON worker.worker_id=worker_maintenance.worker_id "
						+ "WHERE maintenance_id=?"
						);
				preparedStatement.setInt(1, maintenanceId);
				ResultSet resultSet=preparedStatement.executeQuery();
				while(resultSet.next()) {
					if(resultSet.getString("report")!=null 
							&& !resultSet.getString("report").equals("")) {
						int workerId=resultSet.getInt("w_id");
						String firstname=resultSet.getString("worker_firstname");
						String lastname=resultSet.getString("worker_lastname");
						String mail=resultSet.getString("worker_mail");
						Worker worker=new Worker(workerId,firstname,lastname,null,mail,null);
						String reportStr=resultSet.getString("report");
						Maintenance maintenance=new Maintenance();
						maintenance.setMaintenanceId(maintenanceId);
						Report report=new Report(maintenance,worker,reportStr);
						reports.add(report);
					}
					
				}
				
				
			} catch (Exception e) {
				System.out.println("RAPPORT DAO DAO : "+e.getMessage());
			}
		 return reports;
	}

}
