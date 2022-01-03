package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.enumerations.MachineType;
import be.project.enumerations.OperationState;
import be.project.javabeans.Area;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Maintenance;
import be.project.javabeans.Supplier;
import be.project.javabeans.SupplierMachine;

public class SupplierMachineDAO implements DAO<SupplierMachine> {
	
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	
	public SupplierMachineDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	public ArrayList<SupplierMachine> findAllSuppliersMachines(MachineType machineType) {
		ArrayList<SupplierMachine> machines=new ArrayList<SupplierMachine>();
		String key=getApiKey();
		String responseJSON=resource
				.path("supplier")
				.path("machine")
				.path(String.valueOf(machineType))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		JSONArray jsonArray=new JSONArray(responseJSON);
		ObjectMapper mapper=new ObjectMapper();
		SupplierMachine machine;
		Supplier supplier;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				machine=new SupplierMachine();
				JSONObject obj=(JSONObject) jsonArray.get(i);
				machine.setId(obj.getInt("id"));
				machine.setModel(obj.getString("model"));
				machine.setBrand(obj.getString("brand"));
				if(!obj.isNull("description")) {
					machine.setDescription((String)obj.get("description"));
				}
				machine.setType(machineType);
				machine.setPrice(obj.getDouble("price"));

				supplier=(Supplier) mapper.readValue(obj.get("supplier").toString(), Supplier.class);
				
				machine.setSupplier(supplier);

				machines.add(machine);

			}
		} catch (Exception e) {
			System.out.println("Erreur dans suppliermachineDAO du client : "+e.getMessage());
		}
		return machines;
	}

	@Override
	public boolean insert(SupplierMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(SupplierMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(SupplierMachine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SupplierMachine find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SupplierMachine> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
