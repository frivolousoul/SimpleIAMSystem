<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%!String[][] allInfos;%>


<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="control.js?ver=333"></script>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<script type="text/javascript">
	$(document).ready(function() {
		var inputId = $('input[name="uid"]');
		inputId.attr("read-only", "read-only");
		inputId.css("width", "15%");
	});
<%	allInfos = (String[][]) request.getAttribute("allInfo");
	if (request.getAttribute("result") != null) {
		boolean result = (boolean) request.getAttribute("result");
		if (result) {
%>
			alert("Success!")
<%
		}		else {
%>
			alert("Failed!");
<%
		}
	}
	if (request.getParameter("close") != null) {
%>
		closeWindow();
<%		return;
	}
%>
	
</script>
</head>

<body>
	<div class="container-fluid">

		<h2>Modify Identity Information</h2>
		<form class="form-horizontal" id="form_modify" action="servModify" method="post">
			<%
				String[] attrNames = allInfos[0];
				String[] attrVals = allInfos[1];
				for (int i = 0; i < attrNames.length; i++) {
			%>
			<div class='form-group'>
				<label class="control-label col-sm-2" id="_<%=attrNames[i]%>" for="<%=attrNames[i]%>" onclick="delAttr(this.id)">
					<%=attrNames[i]%>
				</label>
				<div class="col-sm-10">
					<input class="form-control" name="<%=attrNames[i]%>" placeholder="<%=attrNames[i]%>" value="<%=attrVals[i]%>">
					<input type="hidden" name="<%=attrNames[i]%>" value="<%=attrVals[i]%>">
				</div>
			</div>
			<%
				}
			%>
			<label>If you want to add new attribute: </label>

			<div class="form-group">

				<div class="col-sm-3">
					<input class="form-control" type="text" name="newAttr" placeholder="Enter New Attribute Name" />

				</div>

				<div class="col-sm-3">
					<input class="form-control" type="text" name="newVal" placeholder="Enter New Attribute Value" />

				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-12">
					<input class="btn btn-info" type="button" value="Add" onclick="modifyAttr('0')" />
					<input class="btn btn-info" type="button" value="Update" onclick="modifyAttr('1')" />
					<input class="btn btn-info" type="button" value="Delete" onclick="modifyAttr('2')" />
					<input class="btn btn-info" type="button" value="Cancel" />
				</div>
			</div>

			<input type="hidden" name="delAttrName" value="">
			<input type="hidden" id="opcode" name="opcode" />
		</form>

		<br>

		<script>
			setElementAttr("uid", "readonly", "readonly");
		</script>
	</div>
</body>

</html>