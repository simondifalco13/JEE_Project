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

/**
 * Servlet implementation class GetMaintenanceInfosServlet
 */
public class GetMaintenanceInfosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMaintenanceInfosServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init() throws ServletException {
    	super.init();
    	context = getServletContext();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user=(User)session.getAttribute("connectedUser");
		try {
			String idsession = String.valueOf(context.getAttribute("idsession"));
			if (user != null  && idsession == session.getId()) {
				if(user instanceof Worker) {
					int maintenance_id= Integer.valueOf(request.getParameter("id"));
					Maintenance maintenance = new Maintenance();
					maintenance = Maintenance.getMaintenance(maintenance_id);
					request.setAttribute("maintenance", maintenance);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/MaintenanceDetails.jsp");
					dispatcher.forward(request, response);
				}
			}
			else {
				response.sendRedirect("connexion");
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans Getmaintenanceinfoservlet : " + e.getMessage());
		}
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
