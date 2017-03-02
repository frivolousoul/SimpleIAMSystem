<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<%!private IdManager idManager = new IdManager();
	private String[] attrNames;%>

<%
	attrNames = idManager.getLatestIdAttr();
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
<body>
	<div class="container-fluid main-content">
		<div class="page-header">
			<h2>Create New Identity</h2>
		</div>
		<div class="well well-lg" style="width: 40%">

			<form class="form-horizontal" action="servCreate" method="POST">
				<h3>Fill in the information</h3>
				<%
					attrNames = idManager.getLatestIdAttr();
					for (int i = 1; i < attrNames.length; i++) {
				%>
				<div class='form-group'>
					<label class="control-label col-sm-2" for="<%=attrNames[i]%>">
						<%=attrNames[i]%></label>
					<div class="col-sm-10">
						<input class="form-control" name="<%=attrNames[i]%>" placeholder="<%=attrNames[i]%>" value="">
					</div>
				</div>
				<%
					}
				%>
				<div class="form-group">
					<div class="col-sm-offset-5">
						<input type="submit" class="btn btn-info btn-md" value="Create" />
					</div>
				</div>

			</form>
		</div>
	</div>


</body>



</html>