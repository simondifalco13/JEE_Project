package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.FactoryMachineDAO;
import be.project.enumerations.MachineType;
import be.project.enumerations.OperationState;
import be.project.javabeans.Area;
import be.project.javabeans.Maintenance;

public class FactoryMachine extends Machine implements Serializable {


	private static final long serialVersionUID = 551457780751157180L;

	private OperationState operationState;
	private ArrayList<Maintenance> machineMaintenances;
	private ArrayList<Area> machineAreas;
	
	
	public FactoryMachine() {
		
	}

	public FactoryMachine(
			String model, String brand,
			String description, MachineType type,
			OperationState operationState,
			ArrayList<Area> machineAreas) {
		super(model, brand, description, type);
		this.operationState=operationState;
		this.machineAreas=machineAreas;
	}
	
	public FactoryMachine(int id,String model, 
			String brand, String description, 
			MachineType type,OperationState operationState,
			ArrayList<Area> machineAreas,
			ArrayList<Maintenance> maintenances) {
		super(id,model, brand, description, type);
		this.operationState=operationState;
		this.machineAreas=machineAreas;
		this.machineMaintenances=maintenances;
		
	}
	
	
	

	public ArrayList<Area> getMachineAreas() {
		return machineAreas;
	}

	public void setMachineAreas(ArrayList<Area> machineAreas) {
		this.machineAreas = machineAreas;
	}


	public OperationState getOperationState() {
		return operationState;
	}

	public void setOperationState(OperationState operationState) {
		this.operationState = operationState;
	}

	public ArrayList<Maintenance> getMachineMaintenances() {
		return machineMaintenances;
	}

	public void setMachineMaintenances(ArrayList<Maintenance> machineMaintenances) {
		this.machineMaintenances = machineMaintenances;
	}

	public static ArrayList<FactoryMachine> getAllFactoryMachines(Site site) {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		FactoryMachineDAO fmDAO=new FactoryMachineDAO();
		machines=fmDAO.findAllSiteMachine(site);
		return machines;
	}
	
	


}
