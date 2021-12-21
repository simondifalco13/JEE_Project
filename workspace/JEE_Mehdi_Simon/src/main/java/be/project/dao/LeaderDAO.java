package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.Employee;
import be.project.javabeans.Leader;

public class LeaderDAO implements DAO<Leader> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	private static String getApiUrl() {
		Context ctx;
		String api="";
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
		    api= (String) env.lookup("apiUrl");
		} catch (NamingException e) {
			System.out.println("Error to get api url");
		}
		return api;
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
		String responseJSON=resource
				.path("leader")
				.path(String.valueOf(id))
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Leader leader=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			leader=(Leader) mapper.readValue(responseJSON, Leader.class);
			System.out.println("leader : "+leader.getFirstname());
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
