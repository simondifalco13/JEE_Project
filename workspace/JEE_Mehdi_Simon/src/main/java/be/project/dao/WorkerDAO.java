package be.project.dao;

import java.net.URI;
import java.util.ArrayList;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

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
	
	public ArrayList<Worker> findSiteWorker(int siteId){
		ArrayList<Worker> workers=new ArrayList<Worker>();
		String key=getApiKey();
		String responseJSON=resource
				.path("worker")
				.path("site")
				.queryParam("siteNumber",String.valueOf(siteId))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		ObjectMapper mapper=new ObjectMapper();
		JSONArray jsonArray=new JSONArray(responseJSON);
		try {
			for(int i=0;i<jsonArray.length();i++) {
				Worker worker=(Worker) mapper.readValue(jsonArray.get(i).toString(), Worker.class);
				workers.add(worker);
			}
		} catch (Exception e) {
			return null;
		}
		return workers;
	}

	@Override
	public ArrayList<Worker> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
