package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.OperationState;
import be.project.javabeans.FactoryMachine;



public class ManageMachineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageMachineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/JSP/manageMachine.jsp").forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			FactoryMachine machine=(FactoryMachine) session.getAttribute("machineToManage");
			String state=request.getParameter("state");
			if(state!=null) {
				OperationState machineState=OperationState.valueOf(state);
				machine.setOperationState(machineState);
				boolean success=machine.update();
				if(success) {
					//redirection sur page de création de maintenance si waiting...
					if(machineState==OperationState.waitingForMaintenance) {
						session.setAttribute("machine", machine);
						response.sendRedirect("CreateMaintenance");
					}else {
						response.sendRedirect("machines");
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception dans managemachien servlet doPost "+ e.getMessage()+e.toString() );
		}
		
	}

}
