package be.project.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import be.project.dao.MaintenanceDAO;
import be.project.dao.WorkerDAO;
import be.project.enumerations.MaintenanceStatus;
import be.project.models.Report;
import be.project.models.FactoryMachine;
import be.project.models.Leader;
import be.project.models.Worker;

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
	private ArrayList<Report> maintenanceReports;

	public Maintenance() {
		
	}
	
	
	public Maintenance(int id,Date date,LocalTime start,LocalTime end,
			MaintenanceStatus status,
			FactoryMachine machine, Leader leader){
		this.maintenanceId=id;
		this.maintenanceDate=date;
		this.machine=machine;
		this.startTime=start;
		this.endTime=end;
		this.status=status;
		this.maintenanceLeader=leader;
		this.maintenanceReports=new ArrayList<Report>();
		this.maintenanceWorkers=new ArrayList<Worker>();
		
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
		this.maintenanceReports=new ArrayList<Report>();
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
			ArrayList<Report> maintenanceReports) {
		this(id,date,start,machine,status,workers,leader);
		this.maintenanceReports=maintenanceReports;
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

	public ArrayList<Report> getMaintenanceReports() {
		return maintenanceReports;
	}


	public void setMaintenanceReports(ArrayList<Report> maintenanceReports) {
		this.maintenanceReports = maintenanceReports;
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


	public ArrayList<Worker> getMaintenanceWorkers() {
		return maintenanceWorkers;
	}

	public void setMaintenanceWorkers(ArrayList<Worker> maintenanceWorkers) {
		this.maintenanceWorkers = maintenanceWorkers;
	}

	private String calculateDuration(LocalTime start,LocalTime end) {
		long hours = ChronoUnit.HOURS.between(start, end);

        long minutes
            = ChronoUnit.MINUTES.between(start, end) % 60;

        long seconds
            = ChronoUnit.SECONDS.between(start, end) % 60;
        return hours+":"+minutes+":"+seconds;
	}
	public static Maintenance getMaintenance(int maintenance_id) {
		Maintenance maintenance=null;
		MaintenanceDAO dao=new MaintenanceDAO();
		maintenance= dao.find(maintenance_id);
		return maintenance;
	}
	public void addWorker(Worker worker) {
		if(worker!=null) {
			this.getMaintenanceWorkers().add(worker);
		}
	}
	public void addReport(Report report) {
		if(report!=null) {
			this.getMaintenanceReports().add(report);
		}
	}


	public int changeStatusDone() {
		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
		return maintenanceDAO.update1(this);
	}
}