package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.MaintenanceStatus;
import be.project.javabeans.Maintenance;



public class UpdateMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public UpdateMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			request.setAttribute("maintenance", maintenance);
			request.getRequestDispatcher("/WEB-INF/JSP/UpdateMaintenance.jsp").forward(request,response);
		}
		catch(Exception e) {
			System.out.println("Exception dans updateMaintenanceServlet doGet "+ e.getMessage()+e.toString());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			String status=request.getParameter("status");
			if(status!=null) {
				MaintenanceStatus maintenanceStatus=MaintenanceStatus.valueOf(status);
				maintenance.setStatus(maintenanceStatus);
				boolean success=maintenance.updateMaintenance();
				if(success) {
					session.setAttribute("maintenance", maintenance);
					response.sendRedirect("machines");
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans updateMaintenanceServlet doPost "+ e.getMessage()+e.toString());
		}
	}
}
