package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Maintenance;

/**
 * Servlet implementation class ConsultMaintenanceServlet
 */

public class ConsultMaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultMaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			request.setAttribute("maintenance", maintenance);
			request.getRequestDispatcher("/WEB-INF/JSP/ConsultMaintenance.jsp").forward(request,response);
		}else {
			//redirection sur page d'erreur
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//UPDATE MAINTENANCE STATUS + METTRE MAINTENANCE EN GLOBAL. 
		HttpSession session = request.getSession(false);
		if(session!=null) {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			Maintenance maintenance=(Maintenance) session.getAttribute("maintenance");
			session.removeAttribute("maintenance");
			if(request.getAttribute("maintenance")!=null) {
				int maintenanceId= (int) request.getAttribute("maintenance");
				if(maintenanceId==maintenance.getMaintenanceId()) {
					//redirige vers page d'update
				}
			}
		}else {
			//redirection sur page d'erreur
		}
	}

}
