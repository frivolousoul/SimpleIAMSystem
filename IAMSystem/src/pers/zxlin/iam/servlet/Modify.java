/**
 * 
 */
package pers.zxlin.iam.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.zxlin.iam.service.IdManager;

/**
 * This servlet deals with Motify request
 * 
 * @author BoJack
 */
public class Modify extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final transient IdManager idManager = new IdManager();
	private boolean result;

	private void loadInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sid = request.getParameter("selectedId");
		request.getSession().setAttribute("sid", sid);
		request.setAttribute("allInfo", idManager.getIdDetail(sid));
		RequestDispatcher rd = request.getRequestDispatcher("/modify.jsp");
		rd.forward(request, response);
	}

	private void addPrivateAttr(HttpServletRequest request) {
		// add new private attribute
		String uid = request.getParameter("uid");
		String newAttr = request.getParameter("newAttr");
		String newVal = request.getParameter("newVal");
		result = idManager.addIdAttr(uid, newAttr, newVal);
	}

	private void delPrivateAttr(HttpServletRequest request) {
		// delete the specified private attribute
		String uid = request.getParameter("uid");
		String delAttrName = request.getParameter("~deleted");
		result = idManager.delIdAttr(uid, delAttrName);
	}

	private void updateInfo(HttpServletRequest request) {
		// update identity information
		String attr;
		String val;
		String uid = request.getParameter("uid");
		Enumeration<String> attrNames = request.getParameterNames();
		while (attrNames.hasMoreElements()) {
			attr = attrNames.nextElement();
			if ("opcode".equals(attr) || "newAttr".equals(attr) || "newVal".equals(attr))
				continue;
			val = request.getParameter(attr);
			result = idManager.updateAttrs(uid, attr, val);
		}
	}

	private void delId(HttpServletRequest request) {
		// delete identity
		String uid = request.getParameter("uid");
		result = idManager.delete(uid);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		RequestDispatcher rd;
		String opcode;

		Object object = request.getParameter("opcode");
		// if no opcode received, just load the information of identity selected
		if (object == null)
			opcode = "-1";
		else
			opcode = object.toString();
		// execute modification operation
		switch (opcode) {
		case "-1":
			loadInfo(request, response);
			return;
		case "0":
			addPrivateAttr(request);

			break;
		case "1":
			updateInfo(request);

			break;
		case "2":
			delId(request);
			break;
		case "3":
			delPrivateAttr(request);
			break;
		default:
			break;

		}
		// set the operation result
		request.setAttribute("result", result);
		request.setAttribute("allInfo", idManager.getIdDetail(request.getSession().getAttribute("sid").toString()));
		String url = "/modify.jsp?";
		// if the identity is deleted, the newly opened information window should be closed
		if ("2".equals(opcode))
			url += "close=y";
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}
