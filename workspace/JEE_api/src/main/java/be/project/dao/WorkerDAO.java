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
				//find site selon...
				Site site=new Site();
				site.setId(siteId);
				worker=new Worker(id,firstname,lastname,null,mail,site);
				System.out.println("firstname : "+worker.getFirstname());
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
	
	
	public static boolean login(int matricule,String password) {
		try {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT worker_id,worker_password FROM worker WHERE worker_id=?");
			preparedStatement.setInt(1, matricule);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				if(password.equals(resultSet.getString("worker_password"))) {
					System.out.println("SAME");
					return true;
				}
			}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	

}
