package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;

import com.sun.jersey.core.util.MultivaluedMapImpl;

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
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("employee_id", String.valueOf(obj.getEmployee().getSerialNumber()));
		parameters.add("price",String.valueOf(obj.getTotalPrice()));
		parameters.add("supplier_machine_id", String.valueOf(obj.getOrderItems().get(0).getMachine().getId()));

		ClientResponse res=resource
				.path("order")
				.path("create")
				.header("key",key)
				.post(ClientResponse.class,parameters);
		
		int httpResponseCode=res.getStatus();
		URI URIlocation = res.getLocation();
		if(httpResponseCode == 201) {
			success=true;
		}
		return success;
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
