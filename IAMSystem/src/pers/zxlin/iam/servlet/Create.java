/**
 * 
 */
package pers.zxlin.iam.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.zxlin.iam.service.IdManager;

/**
 * @author BoJack
 *
 */
public class Create extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IdManager idManager = new IdManager();
		String msg;
		Map<String, String> allAttr = new HashMap<>();
		Enumeration<String> enumeration = request.getParameterNames();
		String cur;
		while (enumeration.hasMoreElements()) {
			cur = enumeration.nextElement().toString();
			allAttr.put(cur, request.getParameter(cur));
		}
		if (idManager.create(allAttr))
			msg = "The identity is created successfully";
		else
			msg = "Fail to create the identity";

		request.setAttribute("msg", msg);
		RequestDispatcher rd = request.getRequestDispatcher("/create.jsp");
		rd.forward(request, response);
	}
}
