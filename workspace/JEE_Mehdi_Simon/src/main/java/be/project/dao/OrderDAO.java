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

import be.project.javabeans.Maintenance;
import be.project.javabeans.Order;

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
		ObjectMapper mapper=new ObjectMapper();
		try {
			for(int i=0; i<jsonArray.length();i++) {
				JSONObject currentObject=(JSONObject) jsonArray.get(i);
				System.out.println(currentObject);
				Order order=new Order();
				order.setId(currentObject.getInt("id"));
				order.setOrderNumber(currentObject.getInt("orderNumber"));
				order.setTotalPrice(currentObject.getDouble("totalPrice"));
				order.setOrderDate(new Date((long)currentObject.get("orderDate")));
				//recuperer autres objets TO DO 
				
				orders.add(order);
			}
		
		} catch (Exception e) {
			System.out.println("Problème conversion json en objet maintenance dans ORDERDAO : " + e.getMessage());
			return null;
		}
		return orders;
	}

}
