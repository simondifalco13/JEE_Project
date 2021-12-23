package be.project.javabeans;

import java.io.Serializable;

public class Report implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1954302769087152816L;
	private String report;
	private Maintenance maintenance;
	private Worker worker;
	
	public Report() {
		// TODO Auto-generated constructor stub
	}

	public Report(Maintenance maintenance, Worker worker) {
		this.maintenance = maintenance;
		this.worker = worker;
	}

	public Report(Maintenance maintenance, Worker worker,String report) {
		this(maintenance,worker);
		this.report=report;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public Maintenance getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(Maintenance maintenance) {
		this.maintenance = maintenance;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	
	

}
