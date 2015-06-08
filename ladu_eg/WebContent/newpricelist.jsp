<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="db.PriceList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Uus hinnakiri</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
	$(function() {
		$("#date_from").datepicker();
		$("#date_to").datepicker();
	});
</script>
</head>
<body>
<%@ include file="logout.jsp" %>
<form action="pricelist?action=new" method="POST">
		<table border="1">
			<tr>
				<th>staatus</th>
				<td><select name="status" id="status">		
						<option value="pooleli" >pooleli</option>
						<option value="kinnitatud">kinnitatud</option>
				</select></td>
			</tr>
			<tr>
				<th>allahindluse protsent</th>
				<td><input type="text" name="discount"/></td>
			</tr>
			<tr>
				<th>kehtimise algus</th>
				<td><input readonly type="text" name="date_from" id="date_from"/></td>
			</tr>
			<tr>
				<th>kehtimise l6pp</th>
				<td><input readonly type="text" name="date_to" id="date_to"/></td>
			</tr>
			<tr>
				<th>m2rkus</th>
				<td><textarea name="note" rows="8" cols="35"></textarea></td>
			</tr>
		</table>
		<input type="submit" value="salvesta">
	</form>
</body>
</html>