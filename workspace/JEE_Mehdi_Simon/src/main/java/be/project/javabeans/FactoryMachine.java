package be.project.javabeans;

import java.io.Serializable;

import be.project.enumerations.MachineType;
import be.project.enumerations.OperationState;

public class FactoryMachine extends Machine implements Serializable {


	private static final long serialVersionUID = 551457780751157180L;

	private OperationState operationState;
	
	public FactoryMachine() {
		
	}

	public FactoryMachine(String model, String brand, String description, MachineType type,OperationState operationState) {
		super(model, brand, description, type);
		this.setOperationState(operationState);
	}

	public OperationState getOperationState() {
		return operationState;
	}

	public void setOperationState(OperationState operationState) {
		this.operationState = operationState;
	}
	
	
	
	

}
