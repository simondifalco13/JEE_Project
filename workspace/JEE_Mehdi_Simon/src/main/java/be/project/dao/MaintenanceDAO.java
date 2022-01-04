package be.project.dao;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import javax.ws.rs.core.MediaType;


import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.project.javabeans.Employee;
import be.project.javabeans.Maintenance;
import be.project.javabeans.Report;
import be.project.javabeans.Worker;

public class MaintenanceDAO implements DAO<Maintenance> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	
	public MaintenanceDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	@Override
	public boolean insert(Maintenance obj) {
		boolean success=false;
		String key=getApiKey();
		String workers="";
		SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy");
		for(int i=0;i<obj.getMaintenanceWorkers().size();i++) {
			if(i!=0) {
				workers+=";";
			}
			workers+=String.valueOf(obj.getMaintenanceWorkers().get(i).getSerialNumber());
		}
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("date_m", DateFor.format(obj.getMaintenanceDate()));
		parameters.add("start_m",obj.getStartTime().toString());
		parameters.add("status", obj.getStatus().toString());
		parameters.add("leaderId", String.valueOf(obj.getMaintenanceLeader().getSerialNumber()));
		parameters.add("workers", workers);
		parameters.add("machineId", String.valueOf(obj.getMachine().getId()));
		//System.out.println("WORKERS : "+workers+ ", date : "+DateFor.format(obj.getMaintenanceDate())+ "start at : "+obj.getStartTime().toString());
		ClientResponse res=resource
				.path("maintenance")
				.path("create")
				.header("key",key)
				.post(ClientResponse.class,parameters)
				;
		int httpResponseCode=res.getStatus();
		if(httpResponseCode == 201) {
			success=true;
		}
		return success;
	}

	@Override
	public boolean delete(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Maintenance obj) {
		boolean success=false;
		String key=getApiKey();
		String workers="";
		SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy");
		DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm:ss");
		String endTime=obj.getEndTime() == null ? null :obj.getEndTime().toString();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("date_m", DateFor.format(obj.getMaintenanceDate()));
		parameters.add("start_t",timeformat.format(obj.getStartTime()));
		parameters.add("status", obj.getStatus().toString());
		ClientResponse res=resource
				.path("maintenance")
				.path(String.valueOf(obj.getMaintenanceId()))
				.header("key",key)
				.put(ClientResponse.class,parameters)
				;
		int httpResponseCode=res.getStatus();
		if(httpResponseCode == 204) {
			success=true;
		}
		return success;
	}
	public int update1(Maintenance obj) {
		String key=getApiKey();
		boolean success = false;
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("maintenance_id", String.valueOf(obj.getMaintenanceId()));
		parameters.add("maintenanceStatus", String.valueOf(obj.getStatus()));
		ClientResponse res = resource
				.path("maintenance")
				.path(String.valueOf(obj.getMaintenanceId()))
				.path("statusDone")
				.header("key",key)
				.put(ClientResponse.class,parameters);
		
		int httpResponseCode = res.getStatus();
		String sqlcode =res.getStatusInfo().getReasonPhrase();
		 ;
		switch(httpResponseCode) {
			case 204 :
				return 0;
			case 304 : 
				return -1;
			case 417 : 
				return Integer.valueOf(sqlcode);
			case 401 :
				return 401;
			case 406 : 
				return 406;
			default : 
				return -1;
		}
	}

	@Override
	public Maintenance find(int id) {
		String key=getApiKey();
		String responseJSON=resource
				.path("maintenance")
				.path(String.valueOf(id))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Maintenance maintenance=null;
		if(responseJSON.isEmpty()) {
			return null;
		}
		JSONObject json = new JSONObject(responseJSON);
		try {
			maintenance = Maintenance.getMaintenanceByJSONObject(json);
			return maintenance;
		} catch (Exception e) {
			System.out.println("Problème conversion json en objet maintenance dans la maintenanceDAO : " + e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Maintenance> findAll() {
		String key=getApiKey();
		
		ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();

		return maintenances;
	}
	public ArrayList<Maintenance> getAllMaintenances(int workerId){
		String key=getApiKey();
		String responseJSON=resource
				.path("maintenance")
				.path("worker")
				.path(String.valueOf(workerId))
				.path("all")
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
		
		try{
			JSONArray jsonArray= new JSONArray(responseJSON);
			maintenances= Maintenance.getMaintenancesByJSONArray(jsonArray);
		}
		catch (Exception e) {
			System.out.println("Problème dans la récupération du tableau de JSON --> maintenanceDAO du client" + e.getMessage() + e.toString());
			return null;
		}
		return maintenances;
}
}
