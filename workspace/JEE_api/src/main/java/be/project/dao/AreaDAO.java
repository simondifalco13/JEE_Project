package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.project.enumerations.ColorCode;
import be.project.enumerations.MachineType;
import be.project.enumerations.OperationState;
import be.project.models.Area;
import be.project.models.Site;

public class AreaDAO implements DAO<Area> {

	@Override
	public boolean delete(Area obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Area obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Area find(int id) {
		Area area=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT  "
					+ "section,dangerousness,areas.site_id AS site_ida,"
					+ "city,address "
					+ "FROM areas "
					+ "LEFT JOIN site ON site.site_id=areas.site_id "
					+ "WHERE areas_id=?"
					);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				String section=resultSet.getString("section");
				ColorCode areaDangerousness=ColorCode.valueOf(resultSet.getString("dangerousness"));
				int site_id=resultSet.getInt("site_ida");
				String city=resultSet.getString("city");
				String address=resultSet.getString("address");
				Site site=new Site(site_id,city,address,null,null,null,null);
				area=new Area(id,section,areaDangerousness,site);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//SELECT * FROM areas LEFT JOIN site ON site.site_id=areas.site_id;
		return area;
	}

	@Override
	public ArrayList<Area> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Area obj) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public ArrayList<Area> getMachineAreas(int machineId){
		ArrayList<Area> areas = new ArrayList<Area>();
		int area_id;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT areas_id FROM machine_areas WHERE machine_id=?"
					);
			preparedStatement.setInt(1, machineId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				area_id=resultSet.getInt("areas_id");
				Area area=Area.getArea(area_id);
				areas.add(area);
			}
		} catch (Exception e) {
			System.out.println("[AREADAO]: "+e.getMessage());
		}
		return areas;
	}
	
	public ArrayList<Area> getSiteAreas(int siteId){
		ArrayList<Area> areas = new ArrayList<Area>();
		int area_id;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT DISTINCT areas_id FROM site "
					+ "LEFT JOIN areas ON areas.site_id=site.site_id "
					+ "WHERE site.site_id=?"
					);
			preparedStatement.setInt(1, siteId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				area_id=resultSet.getInt("areas_id");
				Area area=Area.getArea(area_id);
				areas.add(area);
			}
		} catch (Exception e) {
			System.out.println("[AREADAO]: "+e.getMessage());
		}
		return areas;
	}
	
	

}
