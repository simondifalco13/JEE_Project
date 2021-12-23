package be.project.servlets;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;
import be.project.javabeans.Worker;
import be.project.javabeans.Employee;
import be.project.javabeans.Leader;

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
		String errors="";
		int serialNumber;
		String pwd;
		if(request.getParameter("password")==null || request.getParameter("password").isEmpty() || request.getParameter("password").equals("")){
			errors+="Remplissez le mot de passe.";
		}else {
			try {
				serialNumber=Integer.valueOf(request.getParameter("serialNumber"));
				pwd=request.getParameter("password");
				boolean success=User.login(serialNumber, pwd);
				if(success) {
					//find user
					User connectedUser=User.getUser(serialNumber);
					if(connectedUser!=null) {
						Context ctx = new InitialContext();
						Context env = (Context) ctx.lookup("java:comp/env");
						String apiKey=(String) env.getEnvironment().get("apiKey");
						//env.removeFromEnvironment("apiKey");
						//création de la session
						HttpSession session=request.getSession();
						if(!session.isNew()) {
							session.invalidate();
							session=request.getSession();
						}
						session.setAttribute("apiKey", apiKey);
						//getting right type 
						if(connectedUser instanceof Worker) {
							Worker user=(Worker)connectedUser;
							session.setAttribute("connectedUser", user);
						}
						if(connectedUser instanceof Employee) {
							Employee user=(Employee)connectedUser;
							session.setAttribute("connectedUser", user);
						}
						if(connectedUser instanceof Leader) {
							Leader user=(Leader)connectedUser;
							session.setAttribute("connectedUser", user);
						}
						
						//url avec sessionID 
						//redirection
						
					}
				}else {
					errors+="Identifiant ou mot de passe incorect";
				}
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
				errors+=" Entrez un matricule correct : des chiffres uniquement.";
			}
		}
		
		if(!errors.equals("")) {
			request.setAttribute("error", errors);
			doGet(request,response);
		}
		
	}

}
