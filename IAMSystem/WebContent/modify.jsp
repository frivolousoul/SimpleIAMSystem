<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%!String[][] allInfos;%>
<%
	allInfos = (String[][]) request.getAttribute("allInfo");

	if (request.getAttribute("result") != null) {
		boolean result = (boolean) request.getAttribute("result");
		if (result) {
%>
<script>
	alert("Success!")
</script>
<%
	} else {
%>
<script>
	alert("Failed!");
</script>
<%
	}
	}
	if (request.getParameter("close") != null) {
%>
<script>
	closeWindow();
</script>
<%
	return;
	}
%>

<html>
<head>
<script type="text/javascript" src="control.js?ver=333"></script>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>

<body>
	<h2>Modify Identity Information</h2>
	<form id="form_modify" action="servModify" method="post">
		<table>

			<%
				String[] attrNames = allInfos[0];
				String[] attrVals = allInfos[1];
				for (int i = 0; i < attrNames.length; i++) {
			%>

			<tr>
				<div class='form-group'>
					<td>
						<label id="_<%=attrNames[i]%>" for="<%=attrNames[i]%>" onclick="delAttr(this.id)">
							<%=attrNames[i]%>
						</label>
					</td>

					<td>
						<input class="form-control" name="<%=attrNames[i]%>" placeholder="<%=attrNames[i]%>" value="<%=attrVals[i]%>">
						<input type="hidden" name="<%=attrNames[i]%>" value="<%=attrVals[i]%>">
					</td>
				</div>
			</tr>
			<%
				}
			%>
		</table>
		<input type="hidden" name="delAttrName" value="">
		<input id="opcode" type="hidden" name="opcode" />

		<input type="text" name="newAttr" placeholder="New attribute name" />
		<input type="text" name="newVal" placeholder="New attribute value" />
		<input type="button" value="Add" onclick="modifyAttr('0')" />
		<br>
		<input type="button" value="Modify" onclick="modifyAttr('1')" />
		<input type="button" value="Delete" onclick="modifyAttr('2')" />
		<input type="button" value="Cancel" />

	</form>

	<br>

	<script>
		setElementAttr("uid", "readonly", "readonly");
	</script>

</body>

</html>