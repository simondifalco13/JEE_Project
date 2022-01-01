package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.Maintenance;
import be.project.javabeans.User;
import be.project.javabeans.Worker;

public class WorkerDAO implements DAO<Worker> {
	
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	
	public WorkerDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	@Override
	public boolean insert(Worker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Worker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Worker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Worker find(int id) {
		String key=getApiKey();
		String responseJSON=resource
				.path("worker")
				.path(String.valueOf(id))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Worker worker=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			return worker=(Worker) mapper.readValue(responseJSON, Worker.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<Worker> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<Maintenance> getAllMaintenances(Worker obj){
			String key=getApiKey();
			String responseJSON=resource
					.path("worker")
					.path(String.valueOf(obj.getSerialNumber()))
					.path("maintenances")
					.header("key",key)
					.accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
			
			try{
				JSONArray jsonArray= new JSONArray(responseJSON);
				maintenances= Maintenance.getMaintenancesByJSONArray(jsonArray);
			}
			catch (Exception e) {
				System.out.println("Problème dans la récupération du tableau de JSON --> workerDAO du client" + e.getMessage() + e.toString());
				return null;
			}
			return maintenances;
	}

}
