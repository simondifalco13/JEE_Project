package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.Order;


public class OrdersListingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OrdersListingServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<Order> orders=Order.getAllOrders();
			request.setAttribute("orders", orders);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/OrdersListing.jsp");
			dispatcher.forward(request, response);
		}
		catch(Exception e) {
			System.out.println("Exception dans orderlisting servlet doGet " + e.getMessage()+e.toString());
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
