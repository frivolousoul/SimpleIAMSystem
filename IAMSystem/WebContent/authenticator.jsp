<%@page import="pers.zxlin.iam.service.Authenticator"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<%! 
		private String userName;
		private String pwd;
	%>

</head>

<body>
	 <p>
	 	Status: 
	 	<% 
	 		userName = request.getParameter("userName"); 
	 		pwd = request.getParameter("pwd");
	 		if(Authenticator.authenticate(userName, pwd))
	 			out.println("Login successfully!");
	 		else
	 			out.println("Incorret username or password.");
	 	%>
	 </p>
</body>

</html>