package be.project.api;

import java.sql.Connection;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.dao.DatabaseConnection;
import be.project.models.FactoryMachine;
import be.project.models.Order;
import be.project.models.Employee;
import be.project.models.Item;
import be.project.models.SupplierMachine;

@Path("/order")
public class OrderAPI extends CommunAPI{

	public OrderAPI() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrders(@HeaderParam("key") String key) {
		Connection conn=DatabaseConnection.getInstance();
		if(DatabaseConnection.getError()!=null && conn==null) {
			System.out.println(DatabaseConnection.getError().getJSON());
			return Response.status(Status.OK).entity(DatabaseConnection.getError().getJSON()).build();
		}
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			ArrayList<Order> orders=Order.getAllOrders();
			return Response.status(Status.OK).entity(orders).build();
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	
	@Path("/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(@FormParam("employee_id") int employee_id,
			@FormParam("price") double price,
			@FormParam("supplier_machine_id") int machine_id,
			@HeaderParam("key") String key) {
		try {
			String apiKey=getApiKey();
			if(key!=null) {
				if(key.equals(apiKey)) {
					Employee employee= new Employee();
					employee.setSerialNumber(employee_id);
					SupplierMachine machine = new SupplierMachine();
					machine.setId(machine_id);
					machine.setPrice(price);
					
					Item item = new Item(machine,1);
					Order order = new Order();
					order.setEmployee(employee);
					order.addItem(item);
					
					System.out.println("Total price dans orderapi : " + order.getTotalPrice());
					int code = order.insertOrder();
					if(code==-1) {
						return Response.status(Status.EXPECTATION_FAILED).build();
					}
					else{
						String baseURI=getBaseUri();
						String fullURI=baseURI+"/order/"+code;
						return Response.status(Status.CREATED).header("location",fullURI).build();
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans createOrder de orderAPI" + e.getMessage() + e.toString());
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
}
