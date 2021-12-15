package be.project.javabeans;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import be.project.enumerations.MaintenanceStatus;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Worker;

public class Maintenance implements Serializable {


	private static final long serialVersionUID = -8755427037102783840L;
	
	private int maintenanceId;
	private Date maintenanceDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String duration;
	private MaintenanceStatus status;
	private ArrayList<Worker> maintenanceWorkers;
	private Leader maintenanceLeader;
	private FactoryMachine machine;
	private Map<Worker,String> maintenanceReports;

	public Maintenance() {
		
	}
	
	
	public Maintenance(Date date,LocalTime start,
			FactoryMachine machine, 
			MaintenanceStatus status,
			ArrayList<Worker> workers,
			Leader leader) {
		this.maintenanceDate=date;
		this.machine=machine;
		this.startTime=start;
		this.status=status;
		this.maintenanceWorkers=workers;
		this.maintenanceLeader=leader;
		setMaintenanceReports(new HashMap<Worker,String>());
	}
	
	public Maintenance(int id,Date date,LocalTime start,
			FactoryMachine machine, 
			MaintenanceStatus status,
			ArrayList<Worker> workers,
			Leader leader) {
		this(date,start,machine,status,workers,leader);
		this.maintenanceId=id;
		
	}
	
	public Maintenance(int id,Date date,LocalTime start,
			FactoryMachine machine, 
			MaintenanceStatus status,
			ArrayList<Worker> workers,
			Leader leader,
			LocalTime end,
			 Map<Worker,String> maintenanceReports) {
		this(id,date,start,machine,status,workers,leader);
		this.setMaintenanceReports(maintenanceReports);
		this.endTime=end;
	}
	
	
	public int getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(int maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	public Date getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getDuration() {
		if(this.getEndTime()!=null) {
			duration=calculateDuration(this.getStartTime(),this.getEndTime());
		}
		return duration;
		
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public MaintenanceStatus getStatus() {
		return status;
	}

	public void setStatus(MaintenanceStatus status) {
		this.status = status;
	}

	public ArrayList<Worker> getMaintenanceWorkers() {
		return maintenanceWorkers;
	}

	public void setMaintenanceWorkers(ArrayList<Worker> maintenanceWorkers) {
		this.maintenanceWorkers = maintenanceWorkers;
	}

	public Leader getMaintenanceLeader() {
		return maintenanceLeader;
	}

	public void setMaintenanceLeader(Leader maintenanceLeader) {
		this.maintenanceLeader = maintenanceLeader;
	}

	public FactoryMachine getMachine() {
		return machine;
	}

	public void setMachine(FactoryMachine machine) {
		this.machine = machine;
	}

	

	public Map<Worker,String> getMaintenanceReports() {
		return maintenanceReports;
	}


	public void setMaintenanceReports(Map<Worker,String> maintenanceReports) {
		this.maintenanceReports = maintenanceReports;
	}


	private String calculateDuration(LocalTime start,LocalTime end) {
		long hours = ChronoUnit.HOURS.between(start, end);

        long minutes
            = ChronoUnit.MINUTES.between(start, end) % 60;

        long seconds
            = ChronoUnit.SECONDS.between(start, end) % 60;
        return hours+":"+minutes+":"+seconds;
	}

}
