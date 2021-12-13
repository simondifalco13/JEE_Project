package be.project.dao;

import java.util.ArrayList;

public interface DAO<T> {

	public boolean create(T obj);
	
	public boolean delete(T obj);
	
	public boolean update(T obj);
	
	public T find(int id);
	
	public ArrayList<T> findAll();
	
	
}
