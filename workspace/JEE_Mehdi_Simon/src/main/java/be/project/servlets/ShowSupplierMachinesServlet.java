package be.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.MachineType;
import be.project.javabeans.FactoryMachine;
import be.project.javabeans.SupplierMachine;

/**
 * Servlet implementation class showSupplierMachines
 */
public class ShowSupplierMachinesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowSupplierMachinesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("machineToReplace")!=null) {
			FactoryMachine machineToReplace=(FactoryMachine) session.getAttribute("machineToReplace");
			MachineType machineType=machineToReplace.getType();
			ArrayList<SupplierMachine> suppliersMachines = new ArrayList<SupplierMachine>();
			suppliersMachines = SupplierMachine.getAllSuppliersMachines(machineType);
			request.setAttribute("machines", suppliersMachines);
			request.setAttribute("type", String.valueOf(machineType));
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/SupplierMachines.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(request.getParameter("machinetype")!=null) {
			String machineType= request.getParameter("machinetype");
			if(machineType.toLowerCase().equals("sorting") || machineType.toLowerCase().equals("assembly") || machineType.toLowerCase().equals("production")) {
				ArrayList<SupplierMachine> suppliersMachines = new ArrayList<SupplierMachine>();
				suppliersMachines = SupplierMachine.getAllSuppliersMachines(MachineType.valueOf(machineType.toLowerCase()));
				request.setAttribute("machines", suppliersMachines);
				request.setAttribute("type", String.valueOf(machineType));
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/SupplierMachines.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		if(request.getParameter("suppliermachineid")!=null) {
			int supplierMachineId=Integer.valueOf(request.getParameter("suppliermachineid"));
			session.setAttribute("suppliermachineid", supplierMachineId);
			response.sendRedirect("createorder");
			return;
			
		}
		doGet(request,response);
	}
}
