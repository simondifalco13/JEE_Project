package be.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.MaintenanceStatus;
import be.project.javabeans.*;

/**
 * Servlet implementation class Homepage
 */
public class Homepage extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Homepage() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	ServletContext context = getServletContext();
    	String dbConnection = context.getInitParameter("ConnectionString");
    	if(dbConnection != null) {
    		
    	}
    	else throw new ServletException();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession session = request.getSession();
		Worker worker = new Worker();
		if(!session.isNew()) {
			session.invalidate();
			session = request.getSession();
		}
		if(session.isNew()) {
			
			worker.setFirstname("Mehdi");
			worker.setLastname("Moussaoui");
			worker.setEmail("qsdqs");
			worker.setPassword("qsdq");
			Site site = new Site();
			worker.setSite(site);
			ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
			Maintenance test1 = new Maintenance();
			test1.setStatus(MaintenanceStatus.ongoing);
			Leader leader = new Leader();
			leader.setFirstname("John");
			test1.setMaintenanceLeader(leader);
			Maintenance test2= new Maintenance();
			test2.setStatus(MaintenanceStatus.toDo);
			test2.setMaintenanceLeader(leader);
			
			maintenances.add(test1);
			maintenances.add(test2);
			
			worker.setMaintenances(maintenances);
			
			session.setAttribute("worker", worker);
		}
		if (worker != null ) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Homepage.jsp");
			dispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
