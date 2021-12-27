package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.project.models.Site;
import be.project.models.Worker;

public class WorkerDAO implements DAO<Worker> {

	@Override
	public boolean insert(Worker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Worker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Worker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Worker find(int id) {
		Worker worker=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT worker_id,worker_firstname,worker_lastname,"
					+"worker_mail,"
					+ "worker_password,site_id FROM worker WHERE worker_id=?"
					);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int workerId=resultSet.getInt("worker_id");
				String firstname=resultSet.getString("worker_firstname");
				String lastname=resultSet.getString("worker_lastname");
				String mail=resultSet.getString("worker_mail");
				//mettre ou pas le PWD ? 
				//String pwd=resultSet.getString("worker_password");
				int siteId=resultSet.getInt("site_id");
				Site site=Site.getSite(siteId);
				worker=new Worker(id,firstname,lastname,null,mail,site);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return worker;
	}

	@Override
	public ArrayList<Worker> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public  boolean login(int matricule,String password) {
		try {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT worker_id,worker_password FROM worker WHERE worker_id=?");
			preparedStatement.setInt(1, matricule);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				if(password.equals(resultSet.getString("worker_password"))) {
					return true;
				}
			}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	
	public ArrayList<Worker> getMaintenanceWorker(int maintenanceId){
		ArrayList<Worker> workers=new ArrayList<Worker>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
				"SELECT worker_id FROM worker_maintenance WHERE maintenance_id=?");
			preparedStatement.setInt(1, maintenanceId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int workerId=resultSet.getInt("worker_id");
				Worker worker=find(workerId);
				workers.add(worker);
			}
	
		} catch (Exception e) {
			System.out.println("WORKERDAO :"+e.getMessage());
		}
		return workers;
	}

}
