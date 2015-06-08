<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="model.AttributeModel"%>
<%@page import="db.Item"%>
<%@page import="java.util.List"%>
<%@page import="model.SearchForm"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Toote otsing</title>
</head>
<body>
<%@ include file="logout.jsp" %>
	<h3>Toote otsing</h3>
	<%if(request.getAttribute("form") != null){
	    SearchForm form = (SearchForm) request.getAttribute("form");%>
	
	<form action="" method="POST">
		<table>
		<tr><td>Nimetus</td><td><input type="text" name="name" value="<%=form.getName()%>"/></td></tr>
		<tr><td>Kirjeldus</td><td><input type="text" name="description"  value="<%=form.getDescription()%>"/></td></tr>
		<tr><td>Tootja kood</td><td><input type="text" name="producer_code"  value="<%=form.getProducerCode()%>"/></td></tr>
		<tr><td>Tootja</td><td><input type="text" name="producer" value="<%=form.getProducer()%>" /></td></tr>
		<tr><td>Arv laos</td><td><input type="text" name="quantity" value="<%=form.getQuantity()%>" /></td></tr>
		<tr><td>Müügihind</td><td><input type="text" name="sale_price" value="<%=form.getSalePrice()%>" /></td></tr>
		<tr><td>Laohind</td><td><input type="text" name="store_price" value="<%=form.getStorePrice()%>" /></td></tr>
		<%if(form.getAttributes().size() == 0){
		    out.println("<tr><td>Attribuut</td><td><input type='text' name='attribute' value='"+form.getAttribute()+"'/></td></tr>");
		}else{
		    out.println("<tr><td colspan'2'>------------ attribuudid ---------</td></tr>");
		    out.println("<tr><td>Toote tüüp</td><td>"+form.getType()+
			    "<input type='hidden' name='type' value='"+form.getType()+"'/></td></tr>");
		    for(Long key : form.getAttributes().keySet()){
			AttributeModel current = form.getAttributes().get(key);
			out.println("<tr><td>"+current.getAttributeName()+"</td><td><input type='text' name='"+key+"' value='"
			+current.getAttributeValue()+"' /><input type='hidden' name='"+key+"'value='"+current.getAttributeName()+"' /></td></tr>");
		    }
		} %>
		
		</table>
		<input type="submit" value="Otsi" />
	</form>
	<%} %>
	<div>
		<%
		    if (request.getAttribute("items") != null) {
				List<Item> items = (List<Item>) request.getAttribute("items");
				if (items != null && items.size() > 0) {
		%>
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Nimetus</th>
					<th>Kirjeldus</th>
					<th>Müügi hind</th>
					<th>Lao hind</th>
					<th>Tootja</th>
					<th>Tootja kood</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<%for(Item item : items){
			    out.println("<tr>");
			    out.println("<td>"+item.getItem()+"</td>");
			    out.println("<td>"+item.getName()+"</td>");
			    out.println("<td>"+item.getDescription()+"</td>");
			    out.println("<td>"+getValue(item.getSalePrice())+"</td>");
			    out.println("<td>"+getValue(item.getStorePrice())+"</td>");
			    out.println("<td>"+item.getProducer()+"</td>");
			    out.println("<td>"+item.getProducerCode()+"</td>");
			    out.println("<td><a href='"+request.getContextPath()+"/product?id="+item.getItem()+"'>Muuda</a></td>");
			    out.println("<td><a href='"+request.getContextPath()+"/warehouse?item="+item.getItem()+"'>Lao toiming</a></td>");
			    out.println("</tr>");
			} %>
			</tbody>
		</table>
		<%
		    	}
		    }
		%>
	</div>
</body>
<%! private String getValue(Object o){
    if(o != null){
		return o.toString();
    }else{
		return "";
    }
}%>