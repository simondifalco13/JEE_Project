package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.project.javabeans.Report;

public class ReportDAO implements DAO<Report> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	
	public ReportDAO() {
		ClientConfig config=new DefaultClientConfig();
		client=Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	@Override
	public boolean update(Report report) {
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("maintenance_id", String.valueOf(report.getMaintenance().getMaintenanceId()));
		parameters.add("worker_id", String.valueOf(report.getWorker().getSerialNumber()));
		parameters.add("report", report.getReport());
		ClientResponse res = resource
				.path("maintenance")
				.path(String.valueOf(report.getMaintenance().getMaintenanceId()))
				.path("workerReport")
				.path(String.valueOf(report.getWorker().getSerialNumber()))
				.header("key",key)
				.put(ClientResponse.class,parameters);

		int httpResponseCode = res.getStatus();
		if(httpResponseCode==204) {
			return true;
		}
		else return false;
	}
	
	/*public int updateReport(Report report) {
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("maintenance_id", String.valueOf(report.getMaintenance().getMaintenanceId()));
		parameters.add("worker_id", String.valueOf(report.getWorker().getSerialNumber()));
		parameters.add("report", report.getReport());
		ClientResponse res = resource
				.path("maintenance")
				.path(String.valueOf(report.getMaintenance().getMaintenanceId()))
				.path("workerReport")
				.path(String.valueOf(report.getWorker().getSerialNumber()))
				.header("key",key)
				.put(ClientResponse.class,parameters);

		int httpResponseCode = res.getStatus();
		String sqlcode =res.getStatusInfo().getReasonPhrase();
		switch(httpResponseCode) {
			case 204 :
				return 0;
			case 304 : 
				return -1;
			case 417 : 
				return Integer.valueOf(sqlcode);
			case 401 :
				return 401;
			default : 
				return -1;
		}
	}**/

	@Override
	public boolean delete(Report obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(Report obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Report find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Report> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
