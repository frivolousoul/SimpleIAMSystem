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
public class Modify extends HttpServlet {
	private IdManager idManager = new IdManager();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd;
		boolean result = false;

		String opcode;
		Object object = request.getParameter("opcode");
		if (object == null)
			opcode = "-1";
		else
			opcode = object.toString();

		String uid = request.getParameter("uid");
		String sInfo = request.getQueryString();

		switch (opcode) {
		case "-1":
			String sid = request.getParameter("selectedId");
			request.getSession().setAttribute("sid", sid);
			request.setAttribute("allInfo", idManager.getIdDetail(sid));
			rd = request.getRequestDispatcher("/modify.jsp");
			rd.forward(request, response);
			return;
		case "0":
			String newAttr = request.getParameter("newAttr");
			String newVal = request.getParameter("newVal");
			result = idManager.addIdAttr(uid, newAttr, newVal);
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
			result = idManager.delIdAttr(uid, delAttrName);
			break;
		default:
			break;

		}
		request.setAttribute("result", result);
		request.setAttribute("allInfo", idManager.getIdDetail(request.getSession().getAttribute("sid").toString()));
		String url = "/modify.jsp?";
		if ("2".equals(opcode))
			url += "close=y";
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}
