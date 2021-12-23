package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.Employee;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;



public class EmployeeDAO implements DAO<Employee> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	

	public EmployeeDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	
	@Override
	public boolean insert(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Employee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee find(int id) {
		String key=getApiKey();
		String responseJSON=resource
				.path("employee")
				.path(String.valueOf(id))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Employee employee=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			employee=(Employee) mapper.readValue(responseJSON, Employee.class);
			return employee;
		} catch (Exception e) {
			System.out.println("error = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
