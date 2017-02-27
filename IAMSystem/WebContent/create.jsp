<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">




<html>
<%!private IdManager idManager = new IdManager();
	private String[] attrNames;%>

<%
	attrNames = idManager.getLatestIdAttr();
	Object attrObject = request.getAttribute("msg");
	String msg;
	if (attrObject != null) {
		msg = attrObject.toString();
%>
<script type="text/javascript">
	alert("<%=msg%>
	");
</script>

<%
		RequestDispatcher rd = request.getRequestDispatcher("/main.html");
		rd.forward(request, response);
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="control.js?ver=3"></script>
<title>Create A Identity</title>

</head>

<body>
	<div class="container-fluid main-content">
		<div class="page-header">
			<h2>Create New Identity</h2>
		</div>
		<form action="servCreate" method="POST">
			<div class="well well-lg">
				<h3>Fill in the information</h3>
				<table>
					<%
						attrNames = idManager.getLatestIdAttr();
						for (int i = 1; i < attrNames.length; i++) {
					%>
					<tr>
						<div class='form-group'>
							<td>
								<label for=<%=attrNames[i]%>>
									<%=attrNames[i]%></label>
							</td>
							<td>
								<input class="form-control" name=<%=attrNames[i]%> placeholder=<%=attrNames[i]%> value="">
							</td>
						</div>
					</tr>
					<%
						}
					%>
				</table>
				<input type="submit" class="btn btn-info btn-md" value="Create" />

			</div>
		</form>
	</div>


</body>

</html>