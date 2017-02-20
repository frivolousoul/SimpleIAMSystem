<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>

<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!IdManager idManager = new IdManager();
	String[][] infos;
	String[] attrNames;%>

<%
	Map<String, String> mapAttr = new HashMap<>();
	Enumeration<String> enumPara = request.getParameterNames();
	String attr;
	String val;
	attrNames = idManager.getLatestIdAttr();
	if (enumPara.hasMoreElements()) {
		while (enumPara.hasMoreElements()) {
			attr = enumPara.nextElement();
			val = request.getParameter(attr);
			if (!val.equals(""))
				mapAttr.put(attr, val);
		}
		infos = idManager.searchByAttr(mapAttr);
	} else
		infos = idManager.searchByAttr("1", "1");
%>

<html>
<head>
<script type="text/javascript" src="control.js?ver=1"></script>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
	<h2>Search criteria</h2>

	<form action="search.jsp" method="POST">
		<table>
			<%
				for (int i = 0; i < attrNames.length; i++) {
			%>
			<tr>
				<div class='form-group'>
					<td><label for=<%=attrNames[i]%>> <%=attrNames[i]%></label></td>
					<td><input class="form-control" name=<%=attrNames[i]%>
						placeholder=<%=attrNames[i]%> value=""
					></td>
				</div>
			</tr>
			<%
				}
			%>
		</table>
		<input type="submit" value="Search" />
	</form>



	<input type="hidden" id="selectedItem" value="" />
	<table>
				<tr>
					<th> Selection </th>
					<%
						for (int i = 0; i < attrNames.length; i++){
					%>
						<th> <%=attrNames[i]%> </th>
					<%
						}
					%>
				</tr>

				<%
					for (int i = 0; i < infos.length; i++) {
				%>
				<form action='modify.jsp' method='POST' id='id_form_<%=(i + 1)%>'
					target='_modification'
				>
					<tr>
						<td><input name='idSelected' type='radio'
							onclick='selectItem(<%=(i + 1)%>)'
							value=""
						/></td>
						<%
							String[] curId = infos[i];
								for (int j = 0; j < curId.length; j++) {
						%>
							<td><%=curId[j]%></td>
							<input type='hidden' name=<%=attrNames[j]%> value=<%=curId[j]%> />
						<%
							}
						%>
					</tr>
					
				</form>
				<%
					}
				%>
	</table>

	<input type="button" value="Modify" onclick="openWindow()" />

</body>
</html>