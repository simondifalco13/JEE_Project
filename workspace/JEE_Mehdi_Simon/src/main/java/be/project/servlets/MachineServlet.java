package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;
import be.project.javabeans.Maintenance;


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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			Leader leader=(Leader) session.getAttribute("connectedUser");
			machines=FactoryMachine.getAllFactoryMachinesBySite(leader.getSite());
			request.setAttribute("machines", machines);
			request.getRequestDispatcher("/WEB-INF/JSP/Machines.jsp").forward(request,response);
		}
		catch(Exception e) {
			System.out.println("Exception dans machienservlet doGet "+ e.getMessage()+e.toString());
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(request.getParameter("machine")!=null) {
				if(!request.getParameter("machine").equals("") && !request.getParameter("machine").isEmpty()) {
					int machineToManageId=Integer.valueOf(request.getParameter("machine"));
					 FactoryMachine machineToManage=new FactoryMachine();
					 for(int i=0;i<machines.size();i++) {
						 if(machines.get(i).getId()==machineToManageId) {
							 machineToManage=machines.get(i);
						 }
					 }
					 HttpSession session = request.getSession(false);
					 session.setAttribute("machineToManage", machineToManage);
					 
					 response.sendRedirect("ManageMachine");
				}
			}
			if(request.getParameter("maintenance")!=null) {
				if(!request.getParameter("maintenance").equals("") && !request.getParameter("maintenance").isEmpty()) {
					int maintenanceId=Integer.valueOf(request.getParameter("maintenance"));
					Maintenance maintenanceToConsult = null;
					for(int i=0;i<machines.size();i++) {
						FactoryMachine currentMachine=machines.get(i);
						for(int j=0;j<currentMachine.getMachineMaintenances().size();j++) {
							if(currentMachine.getMachineMaintenances().get(j).getMaintenanceId()==maintenanceId) {
								maintenanceToConsult=currentMachine.getMachineMaintenances().get(j);
							}
						}
					}
					HttpSession session = request.getSession(false);

					if(maintenanceToConsult!=null) {
						session.setAttribute("maintenance", maintenanceToConsult);
						response.sendRedirect("ConsultMaintenance");
					}else {
						 doGet(request,response);
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans machienservlet doPost "+ e.getMessage()+e.toString());
		}
		
	}
}
