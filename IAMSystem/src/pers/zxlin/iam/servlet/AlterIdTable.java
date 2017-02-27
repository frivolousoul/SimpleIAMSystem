/**
 * 
 */
package pers.zxlin.iam.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.zxlin.iam.service.IdManager;

/**
 * @author BoJack
 *
 */
public class AlterIdTable extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IdManager idManager = new IdManager();
		String opcode = request.getParameter("opcode");
		String addedAttr = request.getParameter("addedFieldName");
		String deletedAttr = request.getParameter("deletedFieldName");
		Enumeration<String> all = request.getParameterNames();
		switch (opcode) {
		case "0":
			// add
			idManager.addTableField(addedAttr);
			break;
		case "1":
			// del
			idManager.deleteTableField(deletedAttr);
			String url = "main.jsp?activated=y";
			response.sendRedirect(url);
			break;
		default:
			break;
		}
	}
}
