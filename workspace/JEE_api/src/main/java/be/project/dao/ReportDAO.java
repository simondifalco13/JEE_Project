package be.project.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import be.project.models.Report;

public class ReportDAO implements DAO<Report> {

	@Override
	public boolean update(Report obj) {
		return false;
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

}
