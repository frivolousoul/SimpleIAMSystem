/**
 * 
 */
package pers.zxlin.iam.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author BoJack
 */
public class SSOFilter implements Filter {
	private String contextPath;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		contextPath = filterConfig.getServletContext().getContextPath();

		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Object secret = req.getSession().getAttribute("LOGIN_USER");
		// check the secret
		if (secret == null || !secret.toString().equals("ADMIN"))
			// no need to login again
			chain.doFilter(request, response);
		else {
			res.sendRedirect(contextPath + "/main.jsp");
		}

	}

}
