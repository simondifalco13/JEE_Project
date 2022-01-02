package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;
import be.project.javabeans.Worker;
import be.project.javabeans.Maintenance;
/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext context=null;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
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
		try {
			String idsession = String.valueOf(context.getAttribute("idsession"));
			User user=(User)session.getAttribute("connectedUser");
			if (user != null && session.getId() == idsession) {
				if(user instanceof Worker) {
					Worker worker =(Worker)session.getAttribute("connectedUser");
					ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
					maintenances= Worker.getMaintenances(worker);
					worker.setMaintenances(maintenances);
					
					request.setAttribute("worker", worker);
					request.getRequestDispatcher("/WEB-INF/JSP/HomepageWorker.jsp").forward(request, response);
					return;
				}
				//leader
				//Employee
			}
			else System.out.println("le user est null ou session incorrecte");
			response.sendRedirect("connexion");
			return;
				
		}
		catch(Exception e) {
			System.out.println("Exception générée dans la homeservlet(doGet) : "+ e.getMessage() + e.toString());
			response.sendRedirect("connexion");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
