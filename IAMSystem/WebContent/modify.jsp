<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%!IdManager idManager = new IdManager();%>
<%
	if(request.getAttribute("result")!=null){
	boolean result = (boolean)request.getAttribute("result");
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
%>

<html>
<head>
<script type="text/javascript" src="control.js?ver=2">
	
</script>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>




</head>
<body>
	<h2>Modify Identity Information</h2>
	<form id="form_modify" action="servModifier" method="post">
		<table>

			<%
				String attr;
				String val = "";
				Enumeration<String> attrNames = request.getParameterNames();
				while (attrNames.hasMoreElements()) {
					attr = attrNames.nextElement();
					if ("idSelected".equals(attr) || "opcode".equals(attr) || "newVal".equals(attr)
							|| "newAttr".equals(attr) || "delAttrName".equals(attr) || "~deleted".equals(attr))
						continue;
					val = request.getParameter(attr);
					if ("/".equals(val))
						val = "";
			%>

			<tr>
				<div class='form-group'>
					<td><label id="_<%=attr%>" for="<%=attr%>"
						onclick="delAttr(this.id)"
					> <%=attr%>
					</label></td>
					<td><input class="form-control" name="<%=attr%>" :
						placeholder="<%=attr%>" value="<%=val%>"
					></td>
				</div>
			</tr>
			<%
				}
			%>
		</table>
		<input type="hidden" name="delAttrName" value=""> <input
			id="opcode" type="hidden" name="opcode"
		/> <input type="text" name="newAttr" playholder="New attribute name" />
		<input type="text" name="newVal" playholder="New attribute value" />
		<input type="button" value="Add" onclick="setItemValue('0')" /> <br>
		<input type="button" value="Modify" onclick="setItemValue('1')" /> <input
			type="button" value="Delete" onclick="setItemValue('2')"
		/> <input type="button" value="Cancel" />

	</form>

	<br>

	<script>
		setElementAttr("uid", "readonly", "readonly");
	</script>

</body>

</html>