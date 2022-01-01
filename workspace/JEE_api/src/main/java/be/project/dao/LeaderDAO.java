
package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.project.models.Leader;
import be.project.models.Site;
import be.project.models.Worker;

public class LeaderDAO implements DAO<Leader> {

	@Override
	public boolean insert(Leader obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Leader obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Leader obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Leader find(int id) {
		Leader leader=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT leader_id,leader_firstname,leader_lastname,"
					+"leader_mail,"
					+ "leader_password,site_id FROM leader WHERE leader_id=?"
					);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int leaderId=resultSet.getInt("leader_id");
				String firstname=resultSet.getString("leader_firstname");
				String lastname=resultSet.getString("leader_lastname");
				String mail=resultSet.getString("leader_mail");
				int siteId=resultSet.getInt("site_id");
				Site site=Site.getSite(siteId);
				leader=new Leader(id,firstname,lastname,null,mail,site);
			}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return leader;
	}

	@Override
	public ArrayList<Leader> findAll() {
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
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT leader_id,leader_password FROM leader WHERE leader_id=?");
			preparedStatement.setInt(1, matricule);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				if(password.equals(resultSet.getString("leader_password"))) {
					return true;
				}
			}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public ArrayList<Leader> getSiteLeaders(int siteId){
		ArrayList<Leader> leaders=new ArrayList<Leader>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT DISTINCT leader_id "
					+ "FROM site LEFT JOIN leader ON leader.site_id=site.site_id "
					+ "WHERE site.site_id=?"
					);
			preparedStatement.setInt(1, siteId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int leaderId=resultSet.getInt("leader_id");
				Leader leader=Leader.getLeader(leaderId);
				leaders.add(leader);
			}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return leaders;
	}

}
