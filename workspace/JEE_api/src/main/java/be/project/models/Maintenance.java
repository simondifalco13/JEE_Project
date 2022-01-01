package be.project.models;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.project.dao.MaintenanceDAO;
import be.project.dao.WorkerDAO;
import be.project.enumerations.MaintenanceStatus;
import be.project.models.Maintenance;
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
	
	
	
	public Maintenance(int id,Date date,
			FactoryMachine machine, 
			MaintenanceStatus status,
			ArrayList<Worker> workers,
			Leader leader
			) {
		this.maintenanceId=id;
		this.maintenanceDate=date;
		this.machine=machine;
		this.status=status;
		this.maintenanceWorkers=workers;
		this.maintenanceLeader=leader;
	}
	
	public Maintenance(int id,Date date,
			FactoryMachine machine, 
			MaintenanceStatus status,
			ArrayList<Worker> workers,
			Leader leader,
			LocalTime start,
			LocalTime end
			) {
		this(id,date,machine,status,workers,leader);
		this.startTime=start;
		this.endTime=end;
		if(start!=null && end!=null) {
			this.duration=getDuration();
		}
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
	
	public static Maintenance getMaintenancesByJSONObject(JSONObject maintenanceJSON) throws JsonParseException, JsonMappingException, JSONException, IOException{
				Maintenance maintenance=new Maintenance();

				maintenance.setMaintenanceId(maintenanceJSON.getInt("maintenanceId"));
				maintenance.setMaintenanceDate(new Date((long) maintenanceJSON.get("maintenanceDate")));
				if(!maintenanceJSON.isNull("startTime")) {
					JSONObject obStart=(JSONObject) maintenanceJSON.get("startTime");
					LocalTime start=(LocalTime) LocalTime.of(obStart.getInt("hour"),obStart.getInt("minute"),obStart.getInt("second"));
					maintenance.setStartTime(start);
				}
				if(!maintenanceJSON.isNull("endTime")) {
					JSONObject obEnd=(JSONObject) maintenanceJSON.get("endTime");
					LocalTime end=(LocalTime)LocalTime.of(obEnd.getInt("hour"),obEnd.getInt("minute"),obEnd.getInt("second"));
					maintenance.setEndTime(end);
				}
				maintenance.setStatus(MaintenanceStatus.valueOf(maintenanceJSON.getString("status")));
				JSONArray workerArray=maintenanceJSON.getJSONArray("maintenanceWorkers");
				ObjectMapper workersMapper=new ObjectMapper();
				ArrayList<Worker>workers=new ArrayList<Worker>();
				for(int j=0;j<workerArray.length();j++) {
					Worker worker=(Worker) workersMapper.readValue(workerArray.get(j).toString(), Worker.class);
					workers.add(worker);
				}
				maintenance.setMaintenanceWorkers(workers);
				
				ObjectMapper leaderMapper=new ObjectMapper();
				Leader leader=(Leader) leaderMapper.readValue(maintenanceJSON.get("maintenanceLeader").toString(), Leader.class);
				maintenance.setMaintenanceLeader(leader);
				
				
				
				if(!maintenanceJSON.isNull("maintenanceReports")) {
					JSONArray reportsArray=maintenanceJSON.getJSONArray("maintenanceReports");
					ArrayList<Report> reports=new ArrayList<Report>();
					ObjectMapper reportMapper=new ObjectMapper();
					for(int k=0;k<reportsArray.length();k++) {
						Report report=(Report) reportMapper.readValue(reportsArray.get(k).toString(), Report.class);
						reports.add(report);
					}
					maintenance.setMaintenanceReports(reports);
				}

		return maintenance;
	}

	
	public static ArrayList<Maintenance> getMachineMaintenances(int machineId){
		ArrayList<Maintenance> maintenances=new ArrayList<Maintenance>();
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		maintenances=maintenanceDAO.getMachineMaintenances(machineId);
		return maintenances;
		
	}
	
	public int insertMaintenance() {
		boolean success=false;
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		int createdMaintenanceId=maintenanceDAO.insertMaintenance(this);
		return createdMaintenanceId;
	}
	
	public int updateMaintenance() {
		boolean success=false;
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		int updateCode=maintenanceDAO.updateMaintenance(this);
		return updateCode;
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