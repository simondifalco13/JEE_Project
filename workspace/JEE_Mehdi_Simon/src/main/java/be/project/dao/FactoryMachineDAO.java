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

import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Machine;
import be.project.javabeans.Site;
import be.project.javabeans.Worker;

public class FactoryMachineDAO implements DAO<FactoryMachine> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	
	public FactoryMachineDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	@Override
	public boolean insert(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(FactoryMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FactoryMachine find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<FactoryMachine> findAll() {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		//call API
		return machines;
	}
	
	
	public ArrayList<FactoryMachine> findAllSiteMachine(Site site) {
		ArrayList<FactoryMachine> machines=new ArrayList<FactoryMachine>();
		String key=getApiKey();
		String responseJSON=resource
				.path("factory")
				.path("machine")
				.queryParam("site", String.valueOf(site.getId()))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		JSONArray jsonArray=new JSONArray(responseJSON);
		System.out.println(jsonArray.toString());
		ObjectMapper mapper=new ObjectMapper();
		try {
			for(int i=0;i<jsonArray.length();i++) {
				FactoryMachine machine=(FactoryMachine) mapper.readValue(jsonArray.get(i).toString(),FactoryMachine.class);
				machines.add(machine);
			}
		} catch (Exception e) {
			return null;
		}
		for(int i=0;i<machines.size();i++) {
			System.out.println("Machine : "+machines.get(i).getModel());
		}
		return machines;
	}

}
