package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public interface DAO<T> {
	public boolean insert(T obj);
	
	public boolean delete(T obj);
	
	public boolean update(T obj);
	
	public T find(int id);
	
	public ArrayList<T> findAll();
	
	public static String getApiUrl() {
		Context ctx;
		String api="";
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
		    api= (String) env.lookup("apiUrl");
		} catch (NamingException e) {
			System.out.println("Error to get api url");
		}
		return api;
	}
}
