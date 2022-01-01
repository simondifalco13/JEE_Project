package be.project.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Maintenance obj) {
		return false;
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
}
