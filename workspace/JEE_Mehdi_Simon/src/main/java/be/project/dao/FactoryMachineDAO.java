package be.project.dao;

import java.io.IOException;
import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.enumerations.MachineType;
import be.project.enumerations.MaintenanceStatus;
import be.project.enumerations.OperationState;
import be.project.javabeans.Area;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Machine;
import be.project.javabeans.Maintenance;
import be.project.javabeans.Report;
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
		String responseJSON;
		responseJSON=resource
				.path("factory")
				.path("machine")
				.queryParam("site", String.valueOf(site.getId()))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		JSONArray jsonArray=new JSONArray(responseJSON);
		ObjectMapper mapper=new ObjectMapper();
		FactoryMachine machine;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				machine=new FactoryMachine();
				JSONObject obj=(JSONObject) jsonArray.get(i);
				machine.setId(obj.getInt("id"));
				machine.setModel(obj.getString("model"));
				machine.setBrand(obj.getString("brand"));
				machine.setDescription(obj.getString("description"));
				machine.setType(MachineType.valueOf(obj.getString("type")));
				machine.setOperationState(OperationState.valueOf(obj.getString("running")));
				
				JSONArray arrayAreas=obj.getJSONArray("machineAreas");
				mapper=new ObjectMapper();
				ArrayList<Area> areas=new ArrayList<Area>();
				for(int j=0;j<arrayAreas.length();j++) {
					Area area=(Area) mapper.readValue(arrayAreas.get(j).toString(), Area.class);
					areas.add(area);
				}
				
				machine.setMachineAreas(areas);
				
				JSONArray arrayMaintenances=obj.getJSONArray("machineMaintenances");
				ArrayList<Maintenance> maintenances=Maintenance.getMaintenancesByJSONArray(arrayMaintenances);
				
				machine.setMachineMaintenances(maintenances);
				
				machines.add(machine);

			}
		} catch (Exception e) {
			return null;
		}
		return machines;
	}

}
