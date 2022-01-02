package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import be.project.javabeans.Maintenance;
import be.project.javabeans.User;
import be.project.javabeans.Worker;


public class GetMaintenanceInfosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context = null;
       
    
    public GetMaintenanceInfosServlet() {
        super();
        
    }
    @Override
    public void init() throws ServletException {
    	super.init();
    	context = getServletContext();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);	
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			String idsession = String.valueOf(context.getAttribute("idsession"));
			if (idsession == session.getId() && request.getParameter("maintenanceId")!=null) {
				int maintenance_id= Integer.valueOf(request.getParameter("maintenanceId"));
				Maintenance maintenance = new Maintenance();
				maintenance = Maintenance.getMaintenance(maintenance_id);
				request.setAttribute("maintenance", maintenance);
				session.setAttribute("maintenance_id", maintenance_id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/MaintenanceDetails.jsp");
				dispatcher.forward(request, response);
			}
			else {
				response.sendRedirect("connexion");
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans Getmaintenanceinfoservlet doPost : " + e.getMessage()+ e.toString());
		}
	}
}
