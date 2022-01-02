package be.project.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.MaintenanceStatus;
import be.project.javabeans.Maintenance;
import be.project.javabeans.User;
import be.project.javabeans.Worker;

/**
 * Servlet implementation class ChangeMaintenanceStatusToDoneServlet
 */
public class MaintenanceDoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceDoneServlet() {
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
		String message="";
		HttpSession session = request.getSession(false);
		User user=(User)session.getAttribute("connectedUser");
		try {
			String idsession = String.valueOf(context.getAttribute("idsession"));
			if (user != null  && idsession == session.getId()) {
				if(user instanceof Worker) {
					int maintenance_id = Integer.valueOf(request.getParameter("id"));
					Maintenance maintenance = new Maintenance();
					maintenance.setMaintenanceId(maintenance_id);
					maintenance.setStatus(MaintenanceStatus.done);
					int code =maintenance.changeStatusDone();
					switch(code) {
						case 0 : 
							message= "Status changing successfully";
							break;
						case -1 : 
							message= "Status not updated";
							break;
						case 401 : 
							System.out.println("Accès non autorisé");
							response.sendRedirect("connexion");
							return;
						case 406: 
							System.out.println("Enum passé n'est pas celui attendu");
							message= "Wrong status";
							break;
						default:
							System.out.println("Exception sql généré dans la procédure stockée : " + code);
							message= "Report not created --> error code : " + code;
							break;
					}
						request.setAttribute("id", maintenance_id);
						request.setAttribute("message", message);
						RequestDispatcher requestDispatcher=request.getRequestDispatcher("maintenanceinfos");
						requestDispatcher.forward(request, response);
				}
			}
		}
		catch(Exception e) {
			System.out.println("Erreur dans la maintenancedoneservlet : " + e.getMessage() + e.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
