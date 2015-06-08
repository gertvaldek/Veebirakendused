<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="db.UserAccount" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sisse logimine</title>
</head>
<%String wrongPassword = (String)request.getAttribute("wrongpass");%>
<%String wrongUser = (String)request.getAttribute("wronguser");%>
<body>
	<h3>Logi sisse:</h3>
	
	<form action="login" method="post">
<table>
<tr><td>Kasutajanimi:</td><td><input type="text" name="userName"/>juhan</td></tr>
<tr><td>Parool:</td><td><input type="password" name="password"/> juurikas34metsX</td></tr>
<tr><td></td><td><input type="submit" value="Logi sisse"/></td></tr>
</table>
</form>
<%if(wrongPassword != null){%>
	<%out.println(wrongPassword); %>
<%}%>
<%if(wrongUser != null){%>
	<%out.println(wrongUser); %>
<%}%>

</body>
</html>