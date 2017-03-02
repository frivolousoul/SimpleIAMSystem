/**
 * 
 */
package pers.zxlin.iam.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.zxlin.iam.service.Authenticator;
import pers.zxlin.iam.service.IdManager;

/**
 * This servlet deals with the login request
 * 
 * @author BoJack
 */
public class Login extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		// get related parameter
		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		// check authentication result
		if (Authenticator.authenticate(userName, pwd)) {
			if (session.getAttribute("attrNames") == null)
				session.setAttribute("attrNames", new IdManager().getLatestIdAttr());
			// set attribute for valid login
			session.setAttribute("LOGIN_USER", "ADMIN");
			response.addCookie(new Cookie("LOGIN_USER", "1"));
			RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
			rd.forward(request, response);
		}

		else {
			// invalid login
			printWriter.print("Status: Invalid username or password");
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.include(request, response);
		}
	}
}
