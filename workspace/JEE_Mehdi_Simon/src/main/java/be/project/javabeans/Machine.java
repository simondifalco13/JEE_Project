package be.project.javabeans;

import be.project.enumerations.MachineType;

public abstract class Machine {

	private String model;
	private String brand;
	private String description;
	private MachineType type;
	
	public Machine() {
		
	}
	
	public Machine(String model,String brand,String description,MachineType type) {
		this.model=model;
		this.brand=brand;
		this.description=description;
		this.type=type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MachineType getType() {
		return type;
	}

	public void setType(MachineType type) {
		this.type = type;
	}

}
