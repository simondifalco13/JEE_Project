package be.project.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.Employee;
import be.project.javabeans.Item;
import be.project.javabeans.Maintenance;
import be.project.javabeans.Order;
import be.project.javabeans.SupplierMachine;

public class OrderDAO implements DAO<Order> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}

	public OrderDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	@Override
	public boolean insert(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Order> findAll() {
		ArrayList<Order> orders=new ArrayList<Order>();
		String key=getApiKey();
		String responseJSON=resource
				.path("order")
				.path("all")
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
	
		
		JSONArray jsonArray = new JSONArray(responseJSON);
		orders=Order.getOrderByJSONArray(jsonArray);
		
		return orders;
	}

}
