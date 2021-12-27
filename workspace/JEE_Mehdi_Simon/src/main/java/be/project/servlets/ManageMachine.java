package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.enumerations.OperationState;
import be.project.javabeans.FactoryMachine;

/**
 * Servlet implementation class ManageMachine
 */

public class ManageMachine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageMachine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session!=null) {
			request.getRequestDispatcher("/WEB-INF/JSP/manageMachine.jsp").forward(request,response);

		}else {
			//redirection sur page d'erreur
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			FactoryMachine machine=(FactoryMachine) session.getAttribute("machineToManage");
			String state=request.getParameter("state");
			if(state!=null) {
				OperationState machineState=OperationState.valueOf(state);
				machine.setOperationState(machineState);
				boolean success=machine.update();
				if(success) {
					//redirection sur page de création de maintenance si waiting...
					System.out.println("SUCCESS");
				}
			}
		}else {
			//redirection sur page d'erreur
		}
		
	}

}
