<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@page import="pers.zxlin.iam.service.IdManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!private IdManager idManager = new IdManager();
	private String[] attrNames;%>

<%
	String msg;
	Map<String, String> allAttr = new HashMap<>();
	Enumeration<String> enumeration = request.getParameterNames();
	if (enumeration.hasMoreElements()) {
		String cur;
		while (enumeration.hasMoreElements()) {
			cur = enumeration.nextElement().toString();
			allAttr.put(cur, request.getParameter(cur));
		}
		if (idManager.create(allAttr))
			msg = "The identity is created successfully";
		else
			msg = "Fail to create the identity";
%>
<script type="text/javascript">
			alert('<%=msg%>
	');
</script>

<%
	}
%>


<html>

<head>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"
>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


</head>

<body>
	<h2>Create a identity</h2>
	<form action="create.jsp" method="POST">
		<table>
			<%
				attrNames = idManager.getLatestIdAttr();
				for (int i = 1; i < attrNames.length; i++) {
			%>
			<tr>
				<div class='form-group'>
					<td><label for=<%=attrNames[i]%>> <%=attrNames[i]%></label>
					</td>
					<td><input class="form-control" name=<%=attrNames[i]%>
						placeholder=<%=attrNames[i]%> value=""
					></td>
				</div>
			</tr>
			<%
				}
			%>
		</table>
		<input type="submit" value="Submit" />
	</form>



</body>

</html>