<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="db.UserAccount" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<title>Sisse logimine</title>
</head>

<%String wrongPassword = (String)request.getAttribute("wrongpass");%>
<%String wrongUser = (String)request.getAttribute("wronguser");%>
<style>
.input-group {
	width:400px;
}

</style>
<body>
	<h1>Sisselogimine <small>Veebirakendused</small></h1>
	
	<form action="login" method="post">
	
<div class="input-group"><span class="input-group-addon" id="basic-addon1">Kasutajanimi</span><input type="text" class="form-control" name="userName"/></div>

<div class="input-group"><span class="input-group-addon" id="basic-addon1">Parool</span><input type="password" class="form-control" name="password"/></div>

<input type="submit" class="btn btn-default" value="Logi sisse"/>
</form>
<%if(wrongPassword != null){%>
	<%out.println(wrongPassword); %>
<%}%>
<%if(wrongUser != null){%>
	<%out.println(wrongUser); %>
<%}%>

</body>
</html>