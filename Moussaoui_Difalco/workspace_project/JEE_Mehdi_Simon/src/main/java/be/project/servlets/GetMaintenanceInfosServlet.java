package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import be.project.enumerations.MaintenanceStatus;
import be.project.javabeans.Maintenance;



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
		HttpSession session = request.getSession(false);
		try {
			if(session.getAttribute("maintenanceid")!=null) {
				int maintenance_id=(int)session.getAttribute("maintenanceid");
				Maintenance maintenance = new Maintenance();
				maintenance = Maintenance.getMaintenance(maintenance_id);
				
				request.setAttribute("maintenance", maintenance);
				session.setAttribute("currentmaintenance", maintenance);
				request.getRequestDispatcher("/WEB-INF/JSP/MaintenanceDetails.jsp").forward(request, response);
				return;
			}
			else response.sendRedirect("home");
		}
		catch(Exception e) {
			System.out.println("Exception dans getmaintenanceinfoservlet doget :" +e.getMessage());
		}
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
		
			//Si reviens ici après passer en done ou écrit le rapport
			if(request.getAttribute("message")!=null) {
				doGet(request,response);
				return;
			}
			
			//si cliquer sur write report
			if(request.getParameter("maintenanceid")!=null && session.getAttribute("maintenanceid")!=null) {
				int maintenance_id_session=(int)session.getAttribute("maintenanceid");
				int maintenance_id = Integer.valueOf(request.getParameter("maintenanceid"));
				if(maintenance_id_session == maintenance_id) {
					response.sendRedirect("writereport");
					return;
				}
			}
			
			//si cliquer sur done
			
			String message="";
			if(request.getParameter("maintenanceidfordone")!=null && session.getAttribute("maintenanceid")!=null) {
				int maintenance_id_session=(int)session.getAttribute("maintenanceid");
				int maintenance_id = Integer.valueOf(request.getParameter("maintenanceidfordone"));
				if(maintenance_id_session == maintenance_id) {
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
					request.setAttribute("message", message);
					doGet(request,response);
					return;
				}
				else response.sendRedirect("home");
				return;
			}
			response.sendRedirect("home");	
		}
		catch(Exception e) {
			System.out.println("Exception dans getmaintenanceinfoservlet dopost :" +e.getMessage());
		}
	}
}
