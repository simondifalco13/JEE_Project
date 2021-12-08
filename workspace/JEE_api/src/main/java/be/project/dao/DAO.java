package be.project.dao;

import java.util.ArrayList;

public abstract class DAO<T> {

	public abstract boolean create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);
	
	public abstract T find(int id);
	
	public abstract ArrayList<T> findAll();
	
	
}
