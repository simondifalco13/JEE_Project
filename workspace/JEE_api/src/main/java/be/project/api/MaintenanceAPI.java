package be.project.api;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
			@FormParam("leaderId") int leaderId,
			@FormParam("workers") String workers,
			@FormParam("machineId") int machineId,
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
				Date maintenanceDate = dateFormat.parse(date);
				LocalTime localTime = LocalTime.parse(start, timeformat);
				MaintenanceStatus maintenanceStatus=MaintenanceStatus.valueOf(status);
				Leader leader=new Leader();
				leader.setSerialNumber(leaderId);
				for(int j=0;j<workersId.length;j++) {
					Worker worker=new Worker();
					worker.setSerialNumber(workersId[j]);
					maintenanceWorkers.add(worker);
				}
				FactoryMachine machine=new FactoryMachine();
				machine.setId(machineId);
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
	
	//getMaintenance

}
