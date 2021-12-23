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
	 * @see Filter#destroy()
	 */
	public void destroy() {
		this.filterConfig= null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession(false);
	    HttpServletResponse res = (HttpServletResponse)response;
	    
		if(session != null) {
			try {
				chain.doFilter(request, response);
			}
			catch(Exception ex) {
				System.out.println("Error in the " + this.filterConfig.getFilterName() + " filter!");
			}
		}
		else {
			PrintWriter out = ((HttpServletResponse)response).getWriter();
			out.println("Le filtre " + this.filterConfig.getFilterName() + " a bloqu� la requ�te � la servlet");
			//res.sendRedirect("Authentification");
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig= fConfig;
	}

}
