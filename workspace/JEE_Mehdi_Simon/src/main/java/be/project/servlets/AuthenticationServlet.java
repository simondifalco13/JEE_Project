package be.project.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import be.project.javabeans.User;
import be.project.javabeans.Worker;
import be.project.javabeans.Employee;
import be.project.javabeans.Leader;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String apiKey;

    /**
     * Default constructor. 
     */
    public AuthenticationServlet() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException{
    	super.init();
    	apiKey=getApiKey();
    }
    
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
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null && !session.isNew()) {
			User user = (User)session.getAttribute("connectedUser");
			if(user !=null && user.getSerialNumber()!=0) {
				if(user instanceof Worker) {
					response.sendRedirect("home");
					return;
				}
			}
		}
		request.getRequestDispatcher("/WEB-INF/JSP/authentication.jsp").forward(request,response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
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
						//création de la session
						HttpSession session=request.getSession();
						if(!session.isNew()) {
							session.invalidate();
							session=request.getSession();
						}
						session.setAttribute("apiKey", apiKey);
						context.setAttribute("idsession", session.getId());
						//getting right type 
						if(connectedUser instanceof Worker) {
							Worker user=(Worker)connectedUser;
							session.setAttribute("connectedUser", user);
							context.setAttribute("id", user.getSerialNumber());
							response.sendRedirect("home");
				
						}
						if(connectedUser instanceof Employee) {
							Employee user=(Employee)connectedUser;
							session.setAttribute("connectedUser", user);
							context.setAttribute("id", user.getSerialNumber());
						}
						if(connectedUser instanceof Leader) {
							Leader user=(Leader)connectedUser;
							session.setAttribute("connectedUser", user);
							response.sendRedirect("machines");
						}
						
	
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
