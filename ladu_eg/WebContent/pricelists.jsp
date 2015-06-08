<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="db.PriceList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="pricelistElements" scope="request"
	type="List<PriceList>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hinnakirjad</title>
</head>
<body>
	<%@ include file="logout.jsp"%>
	<%
		String id = "";
		String status = "";
		String discount = "";
		String date_from = "";
		String date_to = "";
		String note = "";
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		out.println("<table border='1'><tr bgcolor=lightgrey><th>kood</th><th>staatus</th><th>allahindluse protsent</th><th>kehtimise algus</th><th>kehtimise l6pp</th><th>m2rkus</th><th></th><th></th></tr>");
		try {
			for (PriceList p : pricelistElements) {
				id = Long.toString(p.getPriceList());
				status = p.getPriceListStatusType().getTypeName();
				discount = Long.toString(p.getDefaultDiscountXtra());
				date_from = df.format(p.getDateFrom());
				date_to = df.format(p.getDateTo());
				note = p.getNote();
				out.println("<tr><td>" + id + "</td><td>" + status
						+ "</td><td>" + discount + "</td><td>" + date_from
						+ "</td><td>" + date_to + "</td><td>"+ note
						+"</td><td><a HREF='pricelist?id="+ id
								+ "'TARGET='_self'><strong>muuda</strong></a></td>"+ 
						"</td><td><a HREF='pricelist?action=delete&uid="+ id
						+ "'TARGET='_self'><strong>kustuta</strong></a></td></tr>");
			}
			out.println("</table><input type=button onclick=\"window.location.href='/pricelist?id=new'\"value='Loo uus'>");
		} catch (Exception ex) {
			out.println("Mingi viga: " + ex.getMessage());
		}		
	%>

</body>
</html>