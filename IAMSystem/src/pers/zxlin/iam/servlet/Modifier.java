/**
 * 
 */
package pers.zxlin.iam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

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
public class Modifier extends HttpServlet {
	private IdManager idManager = new IdManager();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		boolean result = false;
		String opcode = request.getParameter("opcode");
		String uid = request.getParameter("uid");
		String sInfo = request.getQueryString();

		switch (opcode) {
		case "0":
			String newAttr = request.getParameter("newVal");
			String newVal = request.getParameter(newAttr);
			result = idManager.addIdAttribute(newAttr);
			result = idManager.updateAttrs(uid, newAttr, newVal);
			break;
		case "1":
			String attr;
			String val;
			Enumeration<String> attrNames = request.getParameterNames();
			while (attrNames.hasMoreElements()) {
				attr = attrNames.nextElement();
				if ("opcode".equals(attr) || "newAttr".equals(attr) || "newVal".equals(attr))
					continue;
				val = request.getParameter(attr);
				result = idManager.updateAttrs(uid, attr, val);
			}
			break;
		case "2":
			result = idManager.delete(uid);
			break;
		case "3":
			String delAttrName = request.getParameter("~deleted");
			result = idManager.deleteIdAttribute(delAttrName);

			break;
		default:
			break;

		}
		RequestDispatcher rd = request.getRequestDispatcher("/modify.jsp");
		request.setAttribute("result", result);
		rd.forward(request, response);
	}
}
