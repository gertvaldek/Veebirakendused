<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="db.UserAccount"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
UserAccount currentuser=(UserAccount)request.getSession().getAttribute("user");
%>
<% if(currentuser!=null) {%>
<form action="${pageContext.request.contextPath }/" method="post">
<% out.println("<input type=\"submit\" value=\"Logi välja!\"/>"); %>
</form>
<%} %>
<% out.println("<a href='" + request.getContextPath()+"/'>Pealeht</a>"); %>
</body>
</html>