package be.project.dao;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.project.javabeans.Maintenance;

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
		DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HH:mm");
		String endTime=obj.getEndTime() == null ? null :obj.getEndTime().toString();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("date_m", DateFor.format(obj.getMaintenanceDate()));
		parameters.add("start_t",obj.getStartTime().toString());
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

	@Override
	public Maintenance find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Maintenance> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
