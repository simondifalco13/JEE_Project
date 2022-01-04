package be.project.dao;

import java.sql.Connection;
import java.util.ArrayList;

public interface DAO<T> {

	public boolean insert(T obj);
	
	public boolean delete(T obj);
	
	public boolean delete(int id);
	
	public int update(T obj);
	
	public T find(int id);
	
	public ArrayList<T> findAll();
	
	static final Connection conn = DatabaseConnection.getInstance();
	
	
}
