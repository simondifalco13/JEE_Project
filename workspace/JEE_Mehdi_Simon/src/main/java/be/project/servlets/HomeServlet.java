package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

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

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context=null;  

    public HomeServlet() {
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
			String idsession = String.valueOf(context.getAttribute("idsession"));
			if (session.getId() == idsession) {
					Worker worker =(Worker)session.getAttribute("connectedUser");
					ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
					maintenances= Worker.getMaintenances(worker);
					worker.setMaintenances(maintenances);
					request.setAttribute("worker", worker);
					request.getRequestDispatcher("/WEB-INF/JSP/HomepageWorker.jsp").forward(request, response);
					return;
			}
			else System.out.println("session incorrecte");
			response.sendRedirect("connexion");
			return;
				
		}
		catch(Exception e) {
			System.out.println("Exception générée dans la homeservlet(doGet) : "+ e.getMessage() + e.toString());
			response.sendRedirect("connexion");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
