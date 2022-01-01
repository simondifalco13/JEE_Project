package be.project.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Maintenance;
import be.project.javabeans.Report;
import be.project.javabeans.User;
import be.project.javabeans.Worker;

/**
 * Servlet implementation class WriteReportServlet
 */
public class WriteReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user=(User)session.getAttribute("connectedUser");
		if (user != null ) {
			if(user instanceof Worker) {
				try {
					Worker worker =(Worker)user;
					int maintenance_id= Integer.valueOf(request.getParameter("id"));
					request.setAttribute("maintenance_id", maintenance_id);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/CreateReport.jsp");
					dispatcher.forward(request, response);
				}
				catch(Exception e) {
					System.out.println("Error : " + e.getMessage() + e.toString());
				}
			}
		}
		else
			response.sendRedirect("connexion");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", null);
		String error="";
		String message="";
		HttpSession session = request.getSession(false);
		User user=(User)session.getAttribute("connectedUser");
		if (user != null ) {
			if(user instanceof Worker) {
				if(request.getParameter("report")!=null && request.getParameter("id")!=null) {
					try {
						String report = request.getParameter("report");
						int maintenance_id = Integer.valueOf(request.getParameter("id"));
						if(Report.reportIsValid(report) == true) {
							System.out.println("Report ok");
							Maintenance maintenance = new Maintenance();
							maintenance.setMaintenanceId(maintenance_id);
							Worker worker = new Worker();
							worker.setSerialNumber(user.getSerialNumber());
							worker.setLastname(user.getLastname());
							worker.setFirstname(user.getFirstname());
							Report reportObject = new Report(maintenance,worker,report);
							int code = reportObject.createReport();
							request.setAttribute("id", maintenance_id);
							switch(code) {
								case 0 : 
									System.out.println("Créer avec succès");
									message= "Report create";
									break;
								case -1 : 
									System.out.println("Rien n'a été update");
									message= "Report not created";
									break;
								case 401 : 
									System.out.println("Accès non autorisé");
									response.sendRedirect("connexion");
									return;
								default:
									System.out.println("Exception sql généré dans la procédure stockée : " + code);
									message= "Report not created --> error code : " + code;
									break;
							}
							request.setAttribute("message", message);
							RequestDispatcher requestDispatcher=request.getRequestDispatcher("maintenanceinfos");
							requestDispatcher.forward(request, response);
						}
						else {
							error = "A report can't be empty and must contain at least 10 letters";
							request.setAttribute("error", error);
							doGet(request,response);
						}
					}
					catch(Exception e) {
						System.out.println("Error : "+ e.getMessage() + e.toString());
					}
				}
				else {
					error = "A report can't be empty and must contain at least 10 letters";
					request.setAttribute("error", error);
					doGet(request,response);
				}
			}
		}
	}
}
