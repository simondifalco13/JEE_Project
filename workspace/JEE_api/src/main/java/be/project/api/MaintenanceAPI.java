package be.project.api;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Maintenance;
import be.project.models.Report;
import be.project.models.Worker;
import be.project.enumerations.MaintenanceStatus;

@Path("/maintenance")
public class MaintenanceAPI extends CommunAPI {

	public MaintenanceAPI() {
		// TODO Auto-generated constructor stub
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
	@Path("{maintenance_id}")
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