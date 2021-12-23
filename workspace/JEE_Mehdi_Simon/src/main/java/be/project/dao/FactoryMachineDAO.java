package be.project.dao;

import java.util.ArrayList;

import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Machine;
import be.project.javabeans.Site;

public class FactoryMachineDAO implements DAO<FactoryMachine> {

	@Override
	public boolean insert(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FactoryMachine find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<FactoryMachine> findAll() {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		//call API
		return machines;
	}
	
	
	public ArrayList<FactoryMachine> findAllSiteMachine(Site site) {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		//call API
		return machines;
	}

}
