package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.Leader;

public class LeaderDAO implements DAO<Leader> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	
	public LeaderDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	@Override
	public boolean insert(Leader obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Leader obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Leader obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Leader find(int id) {
		String key=getApiKey();
		String responseJSON=resource
				.path("leader")
				.path(String.valueOf(id))
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Leader leader=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			leader=(Leader) mapper.readValue(responseJSON, Leader.class);
			return leader;
		} catch (Exception e) {
			System.out.println("error = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Leader> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
