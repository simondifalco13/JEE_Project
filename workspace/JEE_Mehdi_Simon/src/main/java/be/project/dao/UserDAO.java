package be.project.dao;

import java.util.ArrayList;

import be.project.javabeans.User;

public class UserDAO implements DAO<User> {

	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean insert(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean Authentication(int matricule,String passwor) {
		boolean success=false;
		return success;
	}

}
