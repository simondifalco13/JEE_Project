package be.project.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Employee;
import be.project.javabeans.Item;
import be.project.javabeans.Order;
import be.project.javabeans.SupplierMachine;


public class CreateOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public CreateOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if(session.getAttribute("suppliermachineid")!=null) {
				int supplierMachineId = (int)session.getAttribute("suppliermachineid");
				SupplierMachine machine = SupplierMachine.getSupplierMachine(supplierMachineId);
				request.setAttribute("suppliermachine", machine);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/ValidateOrder.jsp");
				dispatcher.forward(request, response);
			}
			else response.sendRedirect("maintenances");
		}
		catch(Exception e) {
			System.out.println("Exception dans createorderservlet doGet" + e.getMessage());
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		boolean success = false;
		try {
			if(request.getParameter("suppliermachineid")!=null && session.getAttribute("suppliermachineid")!=null) {
				int idInSession = (int)session.getAttribute("suppliermachineid");
				int id = Integer.valueOf(request.getParameter("suppliermachineid"));
				if(id==idInSession) {
					Employee employee= (Employee)session.getAttribute("connectedUser");
					SupplierMachine machine = SupplierMachine.getSupplierMachine(id);

					Item item = new Item(machine,1);
					Order order = new Order();
					order.setEmployee(employee);
					order.addItemWithPrice(item);
									
					success = order.insertOrder();
					if(success) {
						response.sendRedirect("orders");
					}
					else {
						request.setAttribute("error", "Impossible to validate the order please retry later");
						doGet(request,response);
					}
				}
				else response.sendRedirect("maintenances");	
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans createorderservlet doPost" + e.getMessage());
		}
	}
}
