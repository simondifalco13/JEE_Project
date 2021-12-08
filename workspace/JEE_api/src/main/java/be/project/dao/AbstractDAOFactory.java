package be.project.dao;

import be.project.models.*;


public abstract class AbstractDAOFactory {
	
	public abstract DAO<Area> getAreaDAO();
	public abstract DAO<Control> getControlDAO();
	public abstract DAO<Employee> getEmployeeDAO();
	public abstract DAO<FactoryMachine> getFactoryMachineDAO();
	public abstract DAO<Leader> getLeaderDAO();
	public abstract DAO<Maintenance> getMaintenanceDAO();
	public abstract DAO<Order> getOrderDAO();
	public abstract DAO<Site> getSiteDAO();
	public abstract DAO<Supplier> getSupplierDAO();
	public abstract DAO<SupplierMachine> getSupplierMachineDAO();
	public abstract DAO<Worker> getWorkerDAO();
	
	public static AbstractDAOFactory getFactory(){
			return new DAOFactory();
			
	}
}
