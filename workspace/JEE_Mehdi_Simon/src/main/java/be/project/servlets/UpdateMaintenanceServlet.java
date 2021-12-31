package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.MaintenanceStatus;
import be.project.enumerations.OperationState;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Maintenance;

/**
 * Servlet implementation class UpdateMaintenanceServlet
 */

public class UpdateMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			request.setAttribute("maintenance", maintenance);
			request.getRequestDispatcher("/WEB-INF/JSP/UpdateMaintenance.jsp").forward(request,response);
		}else {
			//redirection sur page d'erreur
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			String status=request.getParameter("status");
			if(status!=null) {
				MaintenanceStatus maintenanceStatus=MaintenanceStatus.valueOf(status);
				maintenance.setStatus(maintenanceStatus);
				boolean success=maintenance.updateMaintenance();
				if(success) {
					session.setAttribute("maintenance", maintenance);
					response.sendRedirect("ConsultMaintenance");
				}
			}
		}else {
			//redirection sur page d'erreur
		}
	}

}
