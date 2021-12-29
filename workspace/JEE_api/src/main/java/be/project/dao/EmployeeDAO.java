package be.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.project.models.Employee;
import be.project.models.Site;
import be.project.models.Worker;

public class EmployeeDAO implements DAO<Employee> {

	@Override
	public boolean insert(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee find(int id) {
		Employee emp=null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT employee_id,employee_firstname,employee_lastname,"
					+"employee_mail,"
					+ "employee_password,site_id FROM factory_employee WHERE employee_id=?"
					);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int employeeId=resultSet.getInt("employee_id");
				String firstname=resultSet.getString("employee_firstname");
				String lastname=resultSet.getString("employee_lastname");
				String mail=resultSet.getString("employee_mail");
				//mettre ou pas le PWD ? 
				//String pwd=resultSet.getString("worker_password");
				int siteId=resultSet.getInt("site_id");
				Site site=Site.getSite(siteId);
				emp=new Employee(id,firstname,lastname,null,mail,site);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return emp;
	}

	@Override
	public ArrayList<Employee> findAll() {
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
			PreparedStatement preparedStatement = conn.prepareStatement("SELECT employee_id,employee_password FROM factory_employee WHERE employee_id=?");
			preparedStatement.setInt(1, matricule);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				if(password.equals(resultSet.getString("employee_password"))) {
					return true;
				}
			}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public ArrayList<Employee> getSiteEmployees(int siteId){
		ArrayList<Employee> employees=new ArrayList<Employee>();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(
					"SELECT DISTINCT employee_id FROM site "
					+ "LEFT JOIN factory_employee ON factory_employee.site_id=site.site_id "
					+ "WHERE site.site_id=?"
					);
			preparedStatement.setInt(1, siteId);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int employee_id=resultSet.getInt("employee_id");
				Employee emp=Employee.getEmployee(employee_id);
				employees.add(emp);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return employees;
	}

}
