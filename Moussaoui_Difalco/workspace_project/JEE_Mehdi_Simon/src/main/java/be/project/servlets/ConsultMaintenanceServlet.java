package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Leader;
import be.project.javabeans.Maintenance;


public class ConsultMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ConsultMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			request.setAttribute("maintenance", maintenance);
			request.getRequestDispatcher("/WEB-INF/JSP/ConsultMaintenance.jsp").forward(request,response);
		}
		catch(Exception e) {
			System.out.println("Exception dans consultmaintenance servlet doGet " + e.getMessage() + e.toString());
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//UPDATE MAINTENANCE STATUS + METTRE MAINTENANCE EN GLOBAL. 
		HttpSession session = request.getSession(false);
		try {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			if(request.getParameter("maintenance")!=null) {
				int maintenanceId= Integer.valueOf(request.getParameter("maintenance"));
				if(maintenanceId==maintenance.getMaintenanceId()) {
					response.sendRedirect("UpdateMaintenance");
					return;
				}
			}
			else {
				request.setAttribute("error", "An error occured, please try later");
				doGet(request,response);
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans consultmaintenance servlet doGet " + e.getMessage() + e.toString());
		}	
	} 			
}
