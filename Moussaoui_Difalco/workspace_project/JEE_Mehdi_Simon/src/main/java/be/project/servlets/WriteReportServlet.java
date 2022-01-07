package be.project.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Maintenance;
import be.project.javabeans.Report;
import be.project.javabeans.User;
import be.project.javabeans.Worker;


public class WriteReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public WriteReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if(session.getAttribute("maintenanceid")!=null) {
				int maintenance_id=(int)session.getAttribute("maintenanceid");
				
				request.setAttribute("maintenanceid", maintenance_id);
				request.getRequestDispatcher("/WEB-INF/JSP/CreateReport.jsp").forward(request, response);
				
			}
			else response.sendRedirect("home");
			}
		catch(Exception e) {
			System.out.println("Exception dans writereportservlet doget :" +e.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("error", null);
		String error="";
		String message="";
		try {
			if(session.getAttribute("maintenanceid")!=null) {
				if(request.getParameter("report")!=null && request.getParameter("maintenanceid")!=null) {
						User user = (User)session.getAttribute("connectedUser");
						String report = request.getParameter("report");
						int maintenance_id_session = (int)session.getAttribute("maintenanceid");
						int maintenance_id = Integer.valueOf(request.getParameter("maintenanceid"));
						if(maintenance_id_session != maintenance_id) {
							response.sendRedirect("home");
							return;
						}
						if(Report.reportIsValid(report)) {
							Maintenance maintenance = new Maintenance();
							maintenance.setMaintenanceId(maintenance_id);
							Worker worker = new Worker();
							worker.setSerialNumber(user.getSerialNumber());
							worker.setLastname(user.getLastname());
							worker.setFirstname(user.getFirstname());
							Report reportObject = new Report(maintenance,worker,report);
							boolean success = reportObject.createReportString();
							if(success) {
									message="Report create";
							}
							else {
								message= "Report not created";
							}	
							request.setAttribute("message", message);
							request.getRequestDispatcher("maintenanceinfos").forward(request, response);
							return;
						}
						else {
							error = "A report can't be empty and must contain at least 10 letters";
							request.setAttribute("error", error);
							doGet(request,response);
						}
				}
				else {
					error = "A report can't be empty and must contain at least 10 letters";
					request.setAttribute("error", error);
					doGet(request,response);
				}
			}
			else response.sendRedirect("home");
		}
		catch(Exception e) {
			System.out.println("Exception dans writereportservlet dopost :" +e.getMessage());
		}
	}
}
