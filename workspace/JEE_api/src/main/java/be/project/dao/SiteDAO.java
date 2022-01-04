package be.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Area;
import be.project.models.Employee;
import be.project.models.Leader;
import be.project.models.Site;
import be.project.models.Worker;

public class SiteDAO implements DAO<Site> {

//	public Connection connection;
//	public SiteDAO() {
//		connection=DatabaseConnection.getInstance();
//	}

	@Override
	public boolean insert(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Site obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Site find(int id) {
		Site site=null;
		String city="",address="";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT city,address FROM site WHERE site_id=?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				city=resultSet.getString("city");
				address=resultSet.getString("address");
				//ArrayList<Worker> workers=new WorkerDAO().findSiteWorker(id);
				//ArrayList<Area> areas=new AreaDAO().getSiteAreas(id);
				//ArrayList<Leader> leaders=new LeaderDAO().getSiteLeaders(id);
				//ArrayList<Employee> employees=new EmployeeDAO().getSiteEmployees(id);
				site=new Site(id,city,address,null,null,null,null);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return site;
	}

	@Override
	public ArrayList<Site> findAll() {
		ArrayList<Site> sites=new ArrayList<Site>();
		//faire appel à une méthode du package 
//		Site site=null;
//		String city="",address="";
//		int id;
//		try {
//			PreparedStatement preparedStatement = conn.prepareStatement("SELECT site_id,city,address FROM site");
//			ResultSet resultSet=preparedStatement.executeQuery();
//			while(resultSet.next()) {
//				city=resultSet.getString("city");
//				System.out.println(city);
//				address=resultSet.getString("address");
//				id=resultSet.getInt("site_id");
//				site=new Site(id,city,address);
//				System.out.println("id "+site.getId()+" , city "+site.getCity());
//				sites.add(site);
//			}
//
//		} catch (Exception e) {
//			//
//		}
		return sites;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
