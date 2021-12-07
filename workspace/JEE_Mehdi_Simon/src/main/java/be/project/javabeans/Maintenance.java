package be.project.javabeans;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import be.project.enumerations.MaintenanceStatus;

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

	public Maintenance() {
		
	}
	
	public Maintenance(int id,Date date,LocalTime start,MaintenanceStatus status,ArrayList<Worker> workers,Leader leader) {
		this.maintenanceId=id;
		this.maintenanceDate=date;
		this.startTime=start;
		this.status=status;
		this.maintenanceWorkers=workers;
		this.maintenanceLeader=leader;
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
		duration=calculateDuration(this.getStartTime(),this.getEndTime());
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

	private String calculateDuration(LocalTime start,LocalTime end) {
		long hours = ChronoUnit.HOURS.between(start, end);

        long minutes
            = ChronoUnit.MINUTES.between(start, end) % 60;

        long seconds
            = ChronoUnit.SECONDS.between(start, end) % 60;
        return hours+":"+minutes+":"+seconds;
	}

}
