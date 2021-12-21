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
				System.out.println(success);
				if(success) {
					System.out.println("SUCCESS");
					//find user
					User connectedUser=User.getUser(serialNumber);
					if(connectedUser!=null) {
						System.out.println("User : "+connectedUser.getFirstname());
					}
					//création de session 
					//redirection vers page 
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
