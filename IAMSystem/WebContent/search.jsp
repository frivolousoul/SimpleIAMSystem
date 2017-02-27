<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>

<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!IdManager idManager = new IdManager();
	String[][] infos;
	String[] attrNames;%>

<%
	attrNames = idManager.getLatestIdAttr();
	Object obj = request.getAttribute("infos");
	if (obj != null)
		infos = (String[][]) obj;
	else
		infos = idManager.searchByAttr("1", "1");
%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="css/search.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="control.js?ver=3"></script>

<title>Search For Identity</title>

</head>
<body>



	<div class="table-responsive main-content">
		<form action='servModify' method='POST' id='id_form' target='_modification'>
			<input type="hidden" name="opcode" value="-1" />
			<input type="hidden" name="selectedId" id="selectedItem" value="" />
		</form>
		<form action="servAlterIdTable" method="post" id="form_delTableField">
			<input type="hidden" id="opcode" name="opcode" value="1">
			<input type="hidden" id="deleted" name="deletedFieldName" value="">
		</form>
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>#</th>
					<%
						for (int i = 0; i < attrNames.length; i++) {
					%>
					<th id="<%=attrNames[i]%>" onclick="delTableField(this.id)">
						<%=attrNames[i]%>
					</th>
					<%
						}
					%>
				</tr>
			</thead>

			<tbody>
				<%
					for (int i = 0; i < infos.length; i++) {
						String[] curId = infos[i];
				%>

				<tr>
					<td>
						<input name="idSelected" type="radio" onclick="selectItem('<%=(curId[0])%>')" value="" />
					</td>
					<%
						for (int j = 0; j < curId.length; j++) {
					%>
					<td><%=curId[j]%></td>
					<%
						}
					%>
				</tr>

				<%
					}
				%>
			</tbody>
		</table>
	</div>

	<input type="button" value="Modify" class="btn btn-info" onclick="openWindow()" />
</body>



</html>