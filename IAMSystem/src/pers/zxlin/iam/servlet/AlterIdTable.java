/**
 * 
 */
package pers.zxlin.iam.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.zxlin.iam.service.IdManager;

/**
 * This servlet deals with the altering table request
 * 
 * @author BoJack
 */
public class AlterIdTable extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IdManager idManager = new IdManager();
		// get related parameter
		String opcode = request.getParameter("opcode");
		String addedAttr = request.getParameter("addedFieldName");
		String deletedAttr = request.getParameter("deletedFieldName");
		switch (opcode) {
		case "0":
			// add
			idManager.addTableField(addedAttr);
			break;
		case "1":
			// delete
			idManager.deleteTableField(deletedAttr);
			String url = "main.jsp?activated=y";
			response.sendRedirect(url);
			break;
		default:
			break;
		}
	}
}
