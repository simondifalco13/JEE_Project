package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.Employee;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.Leader;

/**
 * Servlet implementation class HomeEmployeeServlet
 */
public class HomeEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ArrayList<FactoryMachine> machines;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeEmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		machines=FactoryMachine.getAllFactoryMachines();
		request.setAttribute("machines", machines);
		request.getRequestDispatcher("/WEB-INF/JSP/HomePageEmployee.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("machine")!=null) {
			if(!request.getParameter("machine").equals("") && !request.getParameter("machine").isEmpty()) {
				int machineToReplaceId=Integer.valueOf(request.getParameter("machine"));
				 FactoryMachine machineToReplace=new FactoryMachine();
				 for(int i=0;i<machines.size();i++) {
					 if(machines.get(i).getId()==machineToReplaceId) {
						 machineToReplace=machines.get(i);
					 }
				 }
				 HttpSession session = request.getSession(false);
				 session.setAttribute("machineToReplace", machineToReplace);
				 response.sendRedirect("suppliersmachines");
			}
		}
	}

}
