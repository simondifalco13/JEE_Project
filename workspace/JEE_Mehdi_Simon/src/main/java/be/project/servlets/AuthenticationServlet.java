package be.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.project.javabeans.User;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AuthenticationServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/JSP/authentication.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", null);
		int serialNumber;
		String pwd;
		try {
			serialNumber=Integer.valueOf(request.getParameter("serialNumber"));
			pwd=request.getParameter("pwd");
			boolean success=User.login(serialNumber, pwd);
			if(success) {
				System.out.println("SUCCESS");
				//find user
				//redirection selon la classe
			}else {
				request.setAttribute("error", "Identifiant ou mot de passe incorect");
			}
			
		}catch(Exception e) {
			request.setAttribute("error", " Entrez un matricule correct : des des chiffres uniquement.");
			doGet(request,response);
		}
		
		
		
	}

}
