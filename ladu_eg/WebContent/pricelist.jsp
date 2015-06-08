<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="model.CustomerModel"%>
<%@ page import="model.ItemModel"%>
<%@ page import="model.*"%>
<jsp:useBean id="pricelist" scope="request" type="model.PriceListForm" />
<jsp:useBean id="otherstatus" scope="request"
	type="List<java.lang.String>" />
<jsp:useBean id="customers" scope="request" type="List<CustomerModel>" />
<jsp:useBean id="items" scope="request" type="List<ItemModel>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hinnakiri</title>
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
<a href='<%=request.getContextPath()%>/pricelist'>Hinnakirjad</a>
	<form action="pricelist?action=update" method="POST">
		<input type="hidden" name="id" value='<%=pricelist.getId()%>'>
		<table border="1">
			<tr>
				<th>staatus</th>
				<td><select name="status" id="status">
						<option value='<%=pricelist.getPriceListStatusType()%>'><%=pricelist.getPriceListStatusType()%></option>
						<%
							for (String s : otherstatus) {
								out.println("<option value='" + s + "'>" + s + "</option>");
							}
						%>
				</select></td>
			</tr>
			<tr>
				<th>allahindluse protsent</th>
				<td><input type="text" name="discount"
					value="<%=pricelist.getDefaultDiscountXtra()%>" /></td>
			</tr>
			<tr>
				<th>kehtimise algus</th>
				<td><input READONLY type="text" name="date_from" id="date_from"
					value="<%=pricelist.getDateFrom()%>" /></td>
			</tr>
			<tr>
				<th>kehtimise l6pp</th>
				<td><input READONLY type="text" name="date_to" id="date_to"
					value="<%=pricelist.getDateTo()%>" /></td>
			</tr>
			<tr>
				<th>m2rkus</th>
				<td><textarea name="note" rows="8" cols="35"><%=pricelist.getNote()%></textarea></td>
			</tr>
		</table>
		<input type="submit" value="salvesta" />
	</form>
	<br>
	<br>
	<%
		String id = "";
		String name = "";
		out.println("KLIENDID:");
		out.println("<table border='1'><tr bgcolor=lightgrey><th>kood</th><th>nimi</th><th></th></tr>");
		try {
			for (CustomerModel c : customers) {
				id = Integer.toString(c.getId());
				name = c.getName();
				out.println("<tr><td>" + id + "</td><td>" + name
						+ "</td><td><a HREF='pricelist?id="+pricelist.getId()+"&action=deletecustomer&customer="+ id
						+ "'TARGET='_self'><strong>kustuta</strong></a></td></tr>");
			}
			out.println("</table>");
		} catch (Exception ex) {
			out.println("Mingi viga" + ex.getMessage());
		}
	%>
	<form action="pricelist?id=<%=pricelist.getId()%>&action=searchcustomer" method="POST">
		<input type="text" name="customer"> <input type="submit"
			value="Otsi" />
	</form>
	
		<%
		String type;
		List<CustomerModel> searchcustomers;
		if (request.getAttribute("searchcustomers")!=null){
			out.println("Tulemus:<br>");
			searchcustomers = (List<CustomerModel>) request.getAttribute("searchcustomers");
		}else{
			searchcustomers=null;
		}	
		try {
			for (CustomerModel c : searchcustomers) {
				id = Integer.toString(c.getId());
				name = c.getName();
				type = c.getType();
				out.println("<a HREF='pricelist?id="+pricelist.getId()+"&action=addcustomer&customer="+id+"&type="+type
						+ "'TARGET='_self'><strong>"+id+" "+name+" ("+type+")</strong></a><br>");
			}
			out.println("</table>");
		} catch (Exception ex) {}
		
		String item_price_list;
		String discount_xtra;
		String discount_price;
		String sale_price;
		out.println("<br><br>TOOTED:");
		out.println("<table border='1'><tr bgcolor=lightgrey><th>kood</th><th>nimi</th><th>hind allahindluseta</th><th>allahindluse protsent</th><th>hind allahindlusega</th><th></th><th></th></tr>");
		try {
			for (ItemModel i : items) {
				id = Integer.toString(i.getId());
				name = i.getName();
				sale_price = Double.toString(i.getSale_price());
				discount_price = Double.toString(i.getDiscount_price());
				discount_xtra = Long.toString(i.getDiscount_xtra());
				item_price_list = Integer.toString(i.getItem_price_list());
				out.println("<tr><form action='pricelist?id="+pricelist.getId()+"&action=changediscount' method='POST'><input hidden name='item' value='"+id+"'/>");
				out.println("<td><input disabled value='"+id+"'/></td>"
						+"<td><a href="+request.getContextPath()+"/product?id="+id+">"+name+"</a></td>"
						+ "<td><input disabled value='"+sale_price+ "'/></td>"
						+"<td><input type='text' name='discount' value='"+discount_xtra+"'/></td>"
						+ "<td><input disabled value='"+discount_price+"'/></td>"
						+ "<td><input type='submit' value='muuda'/></td>"
						+ "<td><a HREF='pricelist?id="+pricelist.getId()+"&action=deleteitem&item="+ id
						+ "'TARGET='_self'><strong>kustuta</strong></a></td></form></tr>");
			}
			out.println("</table>");
		} catch (Exception ex) {
			out.println("Mingi viga" + ex.getMessage());
		}
	%>
	<form action="pricelist?id=<%=pricelist.getId()%>&action=searchitem" method="POST">
		<input type="text" name="item"> <input type="submit"
			value="Otsi" />
	</form>
	
	<%
		List<ItemModel> searchitems;
		if (request.getAttribute("searchitems")!=null){
			out.println("Tulemus:<br>");
			searchitems = (List<ItemModel>) request.getAttribute("searchitems");
		}else{
			searchitems=null;
		}
		
		try {
			for (ItemModel i : searchitems) {
				id = Integer.toString(i.getId());
				name = i.getName();
				out.println("<a HREF='pricelist?id="+pricelist.getId()+"&action=additem&item="+id
						+ "'TARGET='_self'><strong>"+id+" "+name+"</strong></a><br>");
			}
			out.println("</table>");
		} catch (Exception ex) {}%>
</body>
</html>