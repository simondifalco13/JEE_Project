package be.project.models;

import java.io.Serializable;
import java.util.Date;

public class Control implements Serializable {

	
	private static final long serialVersionUID = -5876450231134059661L;
	private int id;
	private Date controlDate;
	private String observations;
	private Leader leader;
	private FactoryMachine machine;
	
	public Control() {
		// TODO Auto-generated constructor stub
	}
	
	public Control(Date controlDate,String observations,Leader leader,FactoryMachine machine) {
		this.controlDate=controlDate;
		this.observations=observations;
		this.leader=leader;
		this.machine=machine;
	}

	public Date getControlDate() {
		return controlDate;
	}

	public void setControlDate(Date controlDate) {
		this.controlDate = controlDate;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Leader getLeader() {
		return leader;
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	public FactoryMachine getMachine() {
		return machine;
	}

	public void setMachine(FactoryMachine machine) {
		this.machine = machine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
