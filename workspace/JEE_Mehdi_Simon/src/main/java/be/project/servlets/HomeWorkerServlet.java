package be.project.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;
import be.project.javabeans.Worker;
import be.project.javabeans.Maintenance;

public class HomeWorkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context=null;  

    public HomeWorkerServlet() {
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
				session.removeAttribute("maintenanceid");
			}
			Worker worker =(Worker)session.getAttribute("connectedUser");
			ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
			maintenances= Worker.getMaintenances(worker.getSerialNumber());
			worker.setMaintenances(maintenances);
			
			request.setAttribute("worker", worker);
			request.getRequestDispatcher("/WEB-INF/JSP/HomepageWorker.jsp").forward(request, response);
		}
		catch(Exception e) {
			System.out.println("Exception dans homeworkerservlet doget :" +e.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if (request.getParameter("maintenanceid")!=null) {
				int maintenance_id= Integer.valueOf(request.getParameter("maintenanceid"));
				session.setAttribute("maintenanceid", maintenance_id);
				
				response.sendRedirect("maintenanceinfos");
			}
			else {
				doGet(request,response);
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans homeworkerservlet doget :" +e.getMessage());
		}
	}
}
