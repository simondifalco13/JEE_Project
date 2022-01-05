package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.FactoryMachineDAO;
import be.project.enumerations.MachineType;
import be.project.enumerations.OperationState;
import be.project.models.FactoryMachine;

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

	public static ArrayList<FactoryMachine> getAllFactoryMachinesBySite(int siteId) {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		FactoryMachineDAO fmDAO=new FactoryMachineDAO();
		machines=fmDAO.findAllSiteMachine(siteId);
		return machines;
	}
	
	public static ArrayList<FactoryMachine> getAllFactoryMachines() {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		FactoryMachineDAO fmDAO=new FactoryMachineDAO();
		machines=fmDAO.findAll();
		return machines;
	}
	
	public boolean update() {
		int successCode=0;
		boolean success=false;
		FactoryMachineDAO fmDAO=new FactoryMachineDAO();
		successCode=fmDAO.update(this);
		if(successCode==1) {
			success=true;
		}else {
			success=false;
		}
		return success;
	}
	
	public static OperationState getOperationStateFromString(String state) {
		if(state.equals("waitingformaintenance")){
			return OperationState.waitingForMaintenance;
		}
		if(state.equals("toreplace")) {
			
			return OperationState.toReplace;
		}
		else {
			return OperationState.valueOf(state);
		}
	}
}
