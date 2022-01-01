package be.project.javabeans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.project.dao.MaintenanceDAO;
import be.project.enumerations.MaintenanceStatus;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Worker;
import be.project.javabeans.Report;

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

	

	public ArrayList<Report> getMaintenanceReports() {
		return maintenanceReports;
	}


	public void setMaintenanceReports(ArrayList<Report> maintenanceReports) {
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
	public static Maintenance getMaintenance(int id) {
		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
		Maintenance maintenance = maintenanceDAO.find(id);
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
	public static ArrayList<Maintenance> getMaintenancesByJSONArray(JSONArray arrayMaintenances) throws JsonParseException, JsonMappingException, JSONException, IOException{
		ArrayList<Maintenance> maintenances=new ArrayList<Maintenance>();
		for(int i=0;i<arrayMaintenances.length();i++) {
		
			JSONObject currentObject=(JSONObject) arrayMaintenances.get(i);
			Maintenance maintenance=new Maintenance();
			maintenance.setMaintenanceId(currentObject.getInt("maintenanceId"));
			maintenance.setMaintenanceDate(new Date((long) currentObject.get("maintenanceDate")));
			if(!currentObject.isNull("startTime")) {
				JSONObject obStart=(JSONObject) currentObject.get("startTime");
				LocalTime start=(LocalTime) LocalTime.of(obStart.getInt("hour"),obStart.getInt("minute"),obStart.getInt("second"));
				maintenance.setStartTime(start);
			}
				if(!currentObject.isNull("endTime")) {
				JSONObject obEnd=(JSONObject) currentObject.get("endTime");
				LocalTime end=(LocalTime)LocalTime.of(obEnd.getInt("hour"),obEnd.getInt("minute"),obEnd.getInt("second"));
				maintenance.setEndTime(end);
			}
			maintenance.setStatus(MaintenanceStatus.valueOf(currentObject.getString("status")));
			JSONArray workerArray=currentObject.getJSONArray("maintenanceWorkers");
			ObjectMapper workersMapper=new ObjectMapper();
			ArrayList<Worker>workers=new ArrayList<Worker>();
			for(int j=0;j<workerArray.length();j++) {
				Worker worker=(Worker) workersMapper.readValue(workerArray.get(j).toString(), Worker.class);
				workers.add(worker);
			}
			maintenance.setMaintenanceWorkers(workers);
		
			ObjectMapper mapper=new ObjectMapper();
			Leader leader=(Leader) mapper.readValue(currentObject.get("maintenanceLeader").toString(), Leader.class);
			maintenance.setMaintenanceLeader(leader);
		
			FactoryMachine machine = (FactoryMachine) mapper.readValue(currentObject.get("machine").toString(), FactoryMachine.class);
			maintenance.setMachine(machine);
			
			if(!currentObject.isNull("maintenanceReports")) {
				JSONArray reportsArray=currentObject.getJSONArray("maintenanceReports");
				ArrayList<Report> reports=new ArrayList<Report>();
				ObjectMapper reportMapper=new ObjectMapper();
			for(int k=0;k<reportsArray.length();k++) {
				Report report=(Report) reportMapper.readValue(reportsArray.get(k).toString(), Report.class);
				reports.add(report);
			}
			maintenance.setMaintenanceReports(reports);
			}
			maintenances.add(maintenance);
		}
		return maintenances;
		}
	public static Maintenance getMaintenanceByJSONObject(JSONObject jsonObject) throws JsonParseException, JsonMappingException, JSONException, IOException{
		Maintenance maintenance=new Maintenance();
		maintenance.setMaintenanceId(jsonObject.getInt("maintenanceId"));
		maintenance.setMaintenanceDate(new Date((long) jsonObject.get("maintenanceDate")));
		if(!jsonObject.isNull("startTime")) {
			JSONObject obStart=(JSONObject) jsonObject.get("startTime");
			LocalTime start=(LocalTime) LocalTime.of(obStart.getInt("hour"),obStart.getInt("minute"),obStart.getInt("second"));
			maintenance.setStartTime(start);
		}
		if(!jsonObject.isNull("endTime")) {
			JSONObject obEnd=(JSONObject) jsonObject.get("endTime");
			LocalTime end=(LocalTime)LocalTime.of(obEnd.getInt("hour"),obEnd.getInt("minute"),obEnd.getInt("second"));
			maintenance.setEndTime(end);
		}
		maintenance.setStatus(MaintenanceStatus.valueOf(jsonObject.getString("status")));
		JSONArray workerArray=jsonObject.getJSONArray("maintenanceWorkers");
		ObjectMapper workersMapper=new ObjectMapper();
		ArrayList<Worker>workers=new ArrayList<Worker>();
			for(int j=0;j<workerArray.length();j++) {
			Worker worker=(Worker) workersMapper.readValue(workerArray.get(j).toString(), Worker.class);
			workers.add(worker);
		}
		maintenance.setMaintenanceWorkers(workers);
		
		ObjectMapper mapper=new ObjectMapper();
		Leader leader=(Leader) mapper.readValue(jsonObject.get("maintenanceLeader").toString(), Leader.class);
		maintenance.setMaintenanceLeader(leader);
		
		FactoryMachine machine = (FactoryMachine) mapper.readValue(jsonObject.get("machine").toString(), FactoryMachine.class);
		maintenance.setMachine(machine);
		
		if(!jsonObject.isNull("maintenanceReports")) {
			JSONArray reportsArray=jsonObject.getJSONArray("maintenanceReports");
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
	public boolean reportExist() {
		boolean exist = false;
		try {
			for(Report report : this.getMaintenanceReports()) {
				if(report.getReport()!=null) {
					if(!report.getReport().isBlank() && report.getReport().length()>10) {
						exist=true;
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage() + e.toString());
			exist=false;
		}
		return exist;
	}
	public boolean reportAndStatusAllow() {
		boolean allow=false;
		if(this.getStatus()== MaintenanceStatus.ongoing || this.getStatus()== MaintenanceStatus.todo || this.getStatus()== MaintenanceStatus.toredo) {
			allow =true;
		}
		return allow;
	}



	public boolean insertMaintenance() {
		boolean success=false;
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		success=maintenanceDAO.insert(this);
		return success;
	}
	
	public boolean updateMaintenance() {
		boolean success=false;
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		success=maintenanceDAO.update(this);
		return success;
	}
	
	public int changeStatusDone() {
		MaintenanceDAO maintenanceDAO= new MaintenanceDAO();
		return maintenanceDAO.update1(this);

	}
}
