<%@page import="pers.zxlin.iam.service.Authenticator"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Insert title here</title>
<%! 
		private String userName;
		private String pwd;
	%>
</head>
<body>
	<form action="login.jsp" method="POST">
		Username: <input type="text" name="userName"> <br /> Password:
		<input type="password" name="pwd" /> <br /> <input type="submit"
			value="Submit"
		/>
	</form>
	<% if(request.getParameterNames().hasMoreElements()){
	%>
	<p>
		Status:
		<% 
		 		userName = request.getParameter("userName"); 
		 		pwd = request.getParameter("pwd");
		 		if(Authenticator.authenticate(userName, pwd)){
		 			session.setAttribute("LOGIN_USER", "ADMIN");
					response.addCookie(new Cookie("LOGIN_USER","1"));
		 			response.sendRedirect("main.html");
		 		}
		 			
		 		else
		 			out.println("Incorret username or password.");
		 	%>
	</p>
	<%
		}
	%>

</body>
</html>