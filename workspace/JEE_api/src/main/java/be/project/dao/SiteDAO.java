package be.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Site;

public class SiteDAO implements DAO<Site> {

	public Connection connection;
	public SiteDAO() {
		connection=DatabaseConnection.getInstance();
	}

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
	public boolean update(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Site find(int id) {
		Site site=null;
//		String city="",address="";
//		try {
//			PreparedStatement preparedStatement = conn.prepareStatement("SELECT city,address FROM site WHERE site_id=?");
//			preparedStatement.setInt(1, id);
//			ResultSet resultSet=preparedStatement.executeQuery();
//			if(resultSet.next()) {
//				city=resultSet.getString("city");
//				address=resultSet.getString("address");
//				site=new Site(id,city,address);
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		return site;
	}

	@Override
	public ArrayList<Site> findAll() {
		ArrayList<Site> sites=new ArrayList<Site>();
//		Site site=null;
//		String city="",address="";
//		int id;
//		System.out.println("2");
//		try {
//			PreparedStatement preparedStatement = conn.prepareStatement("SELECT site_id,city,address FROM site");
//			ResultSet resultSet=preparedStatement.executeQuery();
//			System.out.println("3");
//			while(resultSet.next()) {
//				city=resultSet.getString("city");
//				System.out.println(city);
//				address=resultSet.getString("address");
//				id=resultSet.getInt("id");
//				//site=new Site(id,city,address);
//				//sites.add(site);
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
