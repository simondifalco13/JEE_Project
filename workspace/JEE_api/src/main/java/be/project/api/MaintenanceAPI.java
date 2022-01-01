package be.project.api;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;


import javax.ws.rs.GET;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.enumerations.MaintenanceStatus;
import be.project.models.FactoryMachine;
import be.project.models.Leader;
import be.project.models.Maintenance;
import be.project.models.Worker;
import be.project.utils.Error;

import be.project.models.Report;



@Path("/maintenance")
public class MaintenanceAPI extends CommunAPI {

	public MaintenanceAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMaintenance(
			@FormParam("date_m") String date,
			@FormParam("start_m") String start,
			@FormParam("status") String status,
			@FormParam("leaderId") String leaderId,
			@FormParam("workers") String workers,
			@FormParam("machineId") String machineId,
			@HeaderParam("key") String key) 
	{
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			ArrayList<Worker> maintenanceWorkers=new ArrayList<Worker>();
			String[] workersSplitted=workers.split(";");
			int[] workersId=new int[workersSplitted.length];
			for(int i=0;i<workersSplitted.length;i++) {
				workersId[i]=Integer.valueOf(workersSplitted[i]);
			}
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm");
			try {
				int idLeader=Integer.valueOf(leaderId);
				int idMachine=Integer.valueOf(machineId);
				Date maintenanceDate = dateFormat.parse(date);
				LocalTime localTime = LocalTime.parse(start, timeformat);
				MaintenanceStatus maintenanceStatus=MaintenanceStatus.valueOf(status);
				Leader leader=new Leader();
				leader.setSerialNumber(idLeader);
				for(int j=0;j<workersId.length;j++) {
					Worker worker=new Worker();
					worker.setSerialNumber(workersId[j]);
					maintenanceWorkers.add(worker);
				}
				FactoryMachine machine=new FactoryMachine();
				machine.setId(idMachine);
				Maintenance maintenance=new Maintenance(maintenanceDate,
						localTime,machine,
						maintenanceStatus,maintenanceWorkers,leader);
				
				int maintenanceId=maintenance.insertMaintenance();
				if(maintenanceId!=0) {
					String baseURI=getBaseUri();
					String fullURI=baseURI+"/maintenance/"+maintenanceId;
					return Response
							.status(Status.CREATED)
							.header("Location", fullURI)
							.build();
				}else {
					return Response.status(Status.SERVICE_UNAVAILABLE).build();
				}
		
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				Error error=Error.UNVALID_DATA_IN_REQ;
				error.setDescription("Some data are not in the right format");
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity(error.getJSON()).build();
			}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateMaintenance(@PathParam("id") int id,
			@FormParam("date_m") String date,
			@FormParam("start_t") String start,
			@FormParam("status") String status,
			@HeaderParam("key") String key) {
		System.out.println("PUT MEHDI");
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			Maintenance maintenance=new Maintenance();
			maintenance.setStartTime(null);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm:ss");
			try {
				Date maintenanceDate = dateFormat.parse(date);
				LocalTime startTime = LocalTime.parse(start, timeformat);
				MaintenanceStatus maintenanceStatus=MaintenanceStatus.valueOf(status);
				maintenance.setStartTime(startTime);
				maintenance.setStatus(maintenanceStatus);
				maintenance.setMaintenanceDate(maintenanceDate);
				maintenance.setMaintenanceId(id);
				int updateCode=maintenance.updateMaintenance();
				if(updateCode==-1){
					return Response.status(Status.NO_CONTENT).build();
				}else {
					Error error=Error.SQL_EXCEPTION;
					return Response.status(Status.OK).entity(error.getJSON()).build();
				}
			} catch (ParseException e) {
				Error error=Error.UNVALID_DATA_IN_REQ;
				error.setDescription("Some data are not in the right format");
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity(error.getJSON()).build();
			
			}
			
			
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}


	
	@GET
	@Path("{maintenance_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMaintenance(@PathParam("maintenance_id") int maintenance_id,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				Maintenance maintenance=Maintenance.getMaintenance(maintenance_id);
				return Response.status(Status.OK).entity(maintenance).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@PUT
	@Path("{maintenance_id}/statusDone")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateMaintenance(@HeaderParam("key") String key,
			@PathParam("maintenance_id")int path_maintenance_id,
			@FormParam("maintenance_id")int maintenance_id,
			@FormParam("maintenanceStatus") String status) {
		String apiKey=getApiKey();
		if(key!=null && path_maintenance_id == maintenance_id ) {
			if(key.equals(apiKey)) {
				try {
					Maintenance maintenance = new Maintenance();
					maintenance.setMaintenanceId(maintenance_id);
					maintenance.setStatus(MaintenanceStatus.valueOf(status));
					int sqlcode =maintenance.changeStatusDone();
						if(sqlcode==0) {
							return Response.status(Status.NO_CONTENT).build();
						}
						else if(sqlcode==-1) {
							return Response.status(Status.NOT_MODIFIED).build();
						}
						else {
							String errorcode = String.valueOf(sqlcode);
							return Response.status(417,errorcode).build();
						}
				}
				catch(Exception e) {
					System.out.println("Exception dans updateMaintenance de maintenanceAPI" + e.getMessage() + e.toString());
					return Response.status(Status.NOT_ACCEPTABLE).build();
				}
				
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
		
	}
	@PUT
	@Path("{maintenance_id}/workerReport/{worker_id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createReport(@HeaderParam("key") String key,
			@PathParam("maintenance_id")int path_maintenance_id,
			@PathParam("worker_id")int path_worker_id,
			@FormParam("maintenance_id")int maintenance_id,
			@FormParam("worker_id")int worker_id,
			@FormParam("report")String reportString) {
		String apiKey=getApiKey();
		if(key!=null && path_maintenance_id==maintenance_id && path_worker_id == worker_id) {
			if(key.equals(apiKey)) {
				Worker worker = new Worker();
				worker.setSerialNumber(worker_id);
				Maintenance maintenance = new Maintenance();
				maintenance.setMaintenanceId(maintenance_id);
				Report report = new Report(maintenance,worker,reportString);
				int sqlcode= report.createReport();
				if(sqlcode==0) {
					return Response.status(Status.NO_CONTENT).build();
				}
				else if(sqlcode==-1) {
					return Response.status(Status.NOT_MODIFIED).build();
				}
				else {
					String errorcode = "Code erreur sql : " + sqlcode;
					return Response.status(417,errorcode).build();
				}
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
}
