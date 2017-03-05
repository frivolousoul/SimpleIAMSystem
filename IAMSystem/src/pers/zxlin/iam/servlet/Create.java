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
 * This servlet deals with the Create request
 * 
 * @author BoJack
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
		String msg, cur, val;
		Map<String, String> allAttr = new HashMap<>();
		Enumeration<String> enumeration = request.getParameterNames();
		// iterate over the parameter enumeration
		while (enumeration.hasMoreElements()) {
			// construct the attribute map for identity creating
			cur = enumeration.nextElement();
			val = request.getParameter(cur);
			if (val == null)
				val = "";
			allAttr.put(cur, val);
		}
		// get result
		if (idManager.create(allAttr))
			msg = "The identity is created successfully";
		else
			msg = "Fail to create the identity";

		request.setAttribute("msg", msg);
		RequestDispatcher rd = request.getRequestDispatcher("/main.jsp?activated=y");
		rd.forward(request, response);
	}
}
