package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import be.project.javabeans.Employee;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Site;

/**
 * Servlet implementation class MachineServlet
 */

public class MachineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String apiKey;
	private static ArrayList<FactoryMachine> machines;
    /**
     * @see HttpServlet#HttpServlet()
     */
	 private String getApiKey() {
    	Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			return (String) env.getEnvironment().get("apiKey");
		} catch (NamingException e) {
			return "";
		}
	}
	 
    public MachineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	apiKey=getApiKey();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session!=null) {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			machines=FactoryMachine.getAllFactoryMachines(leader.getSite());
			request.setAttribute("machines", machines);
			request.getRequestDispatcher("/WEB-INF/JSP/Machines.jsp").forward(request,response);
		}else {
			//redirection sur page d'erreur
		}
    	
		//recuperer Machines 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("machine").equals("") && !request.getParameter("machine").isEmpty()) {
			int machineToManageId=Integer.valueOf(request.getParameter("machine"));
			 FactoryMachine machineToManage=new FactoryMachine();
			 for(int i=0;i<machines.size();i++) {
				 if(machines.get(i).getId()==machineToManageId) {
					 machineToManage=machines.get(i);
				 }
			 }
			 HttpSession session = request.getSession(false);
			 if(session!=null) {
				 session.setAttribute("machineToManage", machineToManage);
				 response.sendRedirect("ManageMachine");
			 }else {
				 //redirection sur page d'erreur
			 }
			 
		}
//		if(!request.getParameter("maintenance").equals("") && !request.getParameter("maintenance").isEmpty()) {
//			//rediriger vers la bonne servlet.
//		}
	}

}
