package be.project.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;

import be.project.javabeans.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class UserDAO implements DAO<User> {
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	
	private static void saveApiKey(String apiKey) {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			env.addToEnvironment("apiKey", apiKey);
		} catch (NamingException e) {
			System.out.println("Error save api key");
		}
	}
	
	
	public UserDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User find(int id) {
		String responseJSON=resource
				.path("user")
				.path(String.valueOf(id))
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		User user=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			user=(User) mapper.readValue(responseJSON, User.class);
			return user=(User) mapper.readValue(responseJSON, User.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean login(int serialNumber,String password) {
		boolean success=false;
		int status;
		MultivaluedMap<String,String> paramsPost=new MultivaluedMapImpl();
		paramsPost.add("serialNumber", String.valueOf(serialNumber));
		paramsPost.add("pwd", password);
		ClientResponse res=resource
				.path("user")
				.path("login")
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class,paramsPost)
				;
		String response=res.getEntity(String.class);
		status=res.getStatus();
		JSONObject jsonResponse = new JSONObject(response);
		if(status==200) {
			//verifier si on est bien connecté
			if(jsonResponse.has("connected")) {
				if(jsonResponse.getString("connected") !=null) {
					String connected=jsonResponse.getString("connected");
					if(connected.equals("true")) {
						//récuperer api-key
						MultivaluedMap<String, String> headers;
						headers=res.getHeaders();
						List<String> apiKey=headers.get("api-key");
						saveApiKey(apiKey.get(0));
						success=true;
					}
				}
			}
		
		}
		return success;
	}

}
