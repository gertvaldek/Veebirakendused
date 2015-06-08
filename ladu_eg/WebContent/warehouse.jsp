<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@page import="db.Item"%>
   <%@page import="db.Store"%>
   <%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lao toimingud</title>
</head>
<%Item item = (Item)request.getAttribute("item");%>
<%List<Store> allStores = (List<Store>)request.getAttribute("allStores");%>
<%String registerSuccessful = (String)request.getAttribute("register_successful"); %>
<%String removeSuccessful = (String)request.getAttribute("remove_successful"); %>
<%String moveFailed = (String)request.getAttribute("move_from_err"); %>
<%String moveSuccessful = (String)request.getAttribute("move_successful"); %>
<%String moveCountError = (String)request.getAttribute("move_from_err_counts"); %>
<%String paramActionNeeded = (String)request.getAttribute("parameter_needed"); %>
<body>
<%@ include file="logout.jsp" %>
<h1>Lao toimingud</h1>
<%if(item != null){ %>
<%if(registerSuccessful != null){ %>
	<%out.println(registerSuccessful); %>
	<br />
	<br />
<%} %>
<%if(removeSuccessful != null){ %>
	<%out.println(removeSuccessful); %>
	<br />
	<br />
<%} %>
<%if(moveFailed != null){ %>
	<%out.println(moveFailed); %>
	<br />
	<br />
<%} %>
<%if(moveCountError != null){ %>
	<%out.println(moveCountError); %>
	<br />
	<br />
<%} %>
<%if(paramActionNeeded != null){ %>
	<%out.println(paramActionNeeded); %>
	<br />
	<br />
<%} %>
<%if(moveSuccessful != null){ %>
	<%out.println(moveSuccessful); %>
	<br />
	<br />
<%} %>
<form method="post" id="warehouse_register_form" accept-charset="UTF-8" action="?action=register">
	<table>
		<tr>
			<td colspan="2">ARVELE VÕTMINE</td>
		</tr>
		<tr>
			<td>Toode:</td>
			<td><%out.println(item.getName() + "- " + item.getDescription() );%>
				<input type="hidden" name="item_id" value="<%out.print(item.getItem());%>" />
			</td>
		</tr>
		<tr>
			<td>Ladu:</td>
			<td>
				<select name="register_select_store">
    				<option value="" disabled="disabled" selected="selected">--Vali ladu--</option>
    				<%for(Store store : allStores){ %>
    					<option value="<%out.print(store.getStore());%>"><%out.print(store.getName());%></option>
    				<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td>Laohind:</td>
			<td><input type="text" name="warehouse_register_price" /></td>
		</tr>
		<tr>
			<td>Kogus:</td>
			<td><input type="text" name="warehouse_register_quantity" /><%out.print(item.getUnitType().getTypeName());%></td>
		</tr>
		<tr>
			<td>Märkus:</td>
			<td>
				<textarea rows="4" cols="50" name="warehouse_register_notes"></textarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="register_submit" value="Salvesta lao toiming" /></td>
		</tr>
	</table>
</form>
	<br />
	<br />
<form method="post" id="warehouse_remove_form" accept-charset="UTF-8" action="?action=remove">
	<table>
		<tr>
			<td colspan="2">MAHA KANDMINE</td>
		</tr>
		<tr>
			<td>Toode:</td>
			<td><%out.println(item.getName() + "- " + item.getDescription() );%>
				<input type="hidden" name="item_id" value="<%out.print(item.getItem());%>" />
			</td>
		</tr>
		<tr>
			<td>Ladu:</td>
			<td>
				<select name="remove_from_store">
    				<option value="" disabled="disabled" selected="selected">--Vali ladu--</option>
    				<%for(Store store : allStores){ %>
    					<option value="<%out.print(store.getStore());%>"><%out.print(store.getName());%></option>
    				<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td>Kogus:</td>
			<td><input type="text" name="warehouse_remove_quantity" /><%out.print(item.getUnitType().getTypeName());%></td>
		</tr>
		<tr>
			<td>Märkus:</td>
			<td>
				<textarea rows="4" cols="50" name="warehouse_remove_notes"></textarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="remove_submit" value="Salvesta lao toiming" /></td>
		</tr>
	</table>
</form>
	<br />
	<br />
<form method="post" id="warehouse_remove_form" accept-charset="UTF-8" action="?action=move">
	<table>
		<tr>
			<td colspan="2">LIIGUTAMINE LADUDE VAHEL</td>
		</tr>
		<tr>
			<td>Toode:</td>
			<td><%out.println(item.getName() + "- " + item.getDescription() );%>
				<input type="hidden" name="item_id" value="<%out.print(item.getItem());%>" />
			</td>
		</tr>
		<tr>
			<td>Laost:</td>
			<td>
				<select name="move_from_store">
    				<option value="" disabled="disabled" selected="selected">--Vali ladu--</option>
    				<%for(Store store : allStores){ %>
    					<option value="<%out.print(store.getStore());%>"><%out.print(store.getName());%></option>
    				<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td>Lattu:</td>
			<td>
				<select name="move_to_store">
    				<option value="" disabled="disabled" selected="selected">--Vali ladu--</option>
    				<%for(Store store : allStores){ %>
    					<option value="<%out.print(store.getStore());%>"><%out.print(store.getName());%></option>
    				<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td>Kogus:</td>
			<td><input type="text" name="warehouse_move_quantity" /><%out.print(item.getUnitType().getTypeName());%></td>
		</tr>
		<tr>
			<td>Märkus:</td>
			<td>
				<textarea rows="4" cols="50" name="warehouse_move_notes"></textarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="move_submit" value="Salvesta lao toiming" /></td>
		</tr>
	</table>
</form>
<%}else{ %>
	Toodet ei leitud!
<%} %>
</body>
</html>