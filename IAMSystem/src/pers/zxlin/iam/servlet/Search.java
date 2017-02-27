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
public class Search extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		IdManager idManager = new IdManager();
		Map<String, String> mapAttr = new HashMap<>();
		Enumeration<String> enumPara = request.getParameterNames();
		String[][] infos;
		String attr;
		String val;
		while (enumPara.hasMoreElements()) {
			attr = enumPara.nextElement();
			val = request.getParameter(attr);
			if (!val.equals(""))
				mapAttr.put(attr, val);
		}
		infos = idManager.searchByAttr(mapAttr);
		request.setAttribute("infos", infos);
		RequestDispatcher rd = request.getRequestDispatcher("/search.jsp");
		rd.forward(request, response);
	}
}
