<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="model.AttributeModel"%>
<%@page import="db.Item"%>
<%@page import="java.util.List"%>
<%@page import="model.SearchForm"%>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Toote otsing</title>
</head>
<style>
.input-group {
	width:400px;
}

</style>
<body>
<%@ include file="logout.jsp" %>
	<h1>Toote otsing <small>Väga mugav</small></h1>

	<%if(request.getAttribute("form") != null){
	    SearchForm form = (SearchForm) request.getAttribute("form");%>
	
	<form action="" method="POST">
		<table>
		<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Nimetus</span>
  <input type="text" class="form-control" name="name" value="<%=form.getName()%>"  aria-describedby="sizing-addon2">
</div><p>
		<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Kirjeldus</span>
  <input type="text" class="form-control" name="description"  value="<%=form.getDescription()%>" aria-describedby="sizing-addon2">
</div><p>
		
	<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Tootja kood</span>
  <input type="text" class="form-control" name="producer_code"  value="<%=form.getProducerCode()%>" aria-describedby="sizing-addon2">
</div><p>
	<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Tootja</span>
  <input type="text" class="form-control" name="producer" value="<%=form.getProducer()%>" aria-describedby="sizing-addon2">
</div><p>
	<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Arv laos</span>
  <input type="text" class="form-control" name="quantity" value="<%=form.getQuantity()%>" aria-describedby="sizing-addon2">
</div><p>
		<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Müügihind</span>
  <input type="text" class="form-control" name="sale_price" value="<%=form.getSalePrice()%>" aria-describedby="sizing-addon2">
</div><p>
		<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Laohind</span>
  <input type="text" class="form-control" name="store_price" value="<%=form.getStorePrice()%>" aria-describedby="sizing-addon2">
</div><p>

		<%if(form.getAttributes().size() == 0){
		    out.println("<tr><td>Attribuut</td><td><input type='text' class='form-control' name='attribute' value='"+form.getAttribute()+"'/></td></tr>");
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
		<input type="submit" class="btn btn-default" value="Otsi" />
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