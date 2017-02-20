/**
 * 
 */
package pers.zxlin.iam.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author BoJack
 *
 */
public class SessionCheckFilter implements javax.servlet.Filter {
	private String contextPath;

	@Override
	public void init(FilterConfig fc) throws ServletException {
		contextPath = fc.getServletContext().getContextPath();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getSession().getAttribute("LOGIN_USER") == null) {
			res.sendRedirect(contextPath + "/login.jsp");
		} else {
			String userType = (String) req.getSession().getAttribute("LOGIN_USER");
			if (!userType.equals("ADMIN")) {
				res.sendRedirect(contextPath + "/login.jsp");
			}
			chain.doFilter(request, response);
		}
	}

}
