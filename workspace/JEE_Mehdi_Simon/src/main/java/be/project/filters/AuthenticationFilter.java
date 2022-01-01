package be.project.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.project.javabeans.User;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {
	
	private FilterConfig filterConfig;
    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		Filter.super.init(fConfig);
		this.filterConfig=fConfig;
	}
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		Filter.super.destroy();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest)request;
		HttpSession session = req.getSession(false);
	    HttpServletResponse res = (HttpServletResponse)response;
		if(session!=null) {
			User user =(User)session.getAttribute("connectedUser");
			if(user !=null && user.getSerialNumber() !=0) {
				try {
					System.out.println("Passer dans le " + this.filterConfig.getFilterName()+ " : pas de problème : currentuser : "+ user.getSerialNumber());
					chain.doFilter(request, response);
				}
				catch(Exception ex) {
					System.out.println("Error in the " + this.filterConfig.getFilterName() + " filter!");
					System.out.println("Error : "+ ex.getMessage());
				}
			}
			else {
				System.out.println("Le filtre " + this.filterConfig.getFilterName() + " a bloqué la requête à la servlet");
				res.sendRedirect("connexion");	
			}
		}
		else {
			System.out.println("Le filtre " + this.filterConfig.getFilterName() + " a bloqué la requête à la servlet");
			res.sendRedirect("connexion");	
		}
		
	}
}
