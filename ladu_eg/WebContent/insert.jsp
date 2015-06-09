<%@page import="model.AttributeModel"%>
<%@page import="model.ProductModel"%>
<%@page import="db.TypeAttribute"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<title>Toote lisamine</title>
</head>
<style>
.input-group {
	width:400px;
}

</style>
<body>
<%@ include file="logout.jsp" %>
<h3>R40 LADU</h3>
<form action="" method="POST">
<table>
<%
if(request.getAttribute("productModel") != null){
    ProductModel model = (ProductModel) request.getAttribute("productModel");
%>
<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Nimetus</span>
  <input type="text" class="form-control" name="name" value="<%=model.getName().getAttributeValue()%>"  aria-describedby="sizing-addon2">
</div><span><%=model.getName().getErrorMessage()%></span><p>
<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Kirjeldus</span>
  <input type="text" class="form-control" name="description" value="<%=model.getDescription().getAttributeValue()%>"  aria-describedby="sizing-addon2">
</div><span><%=model.getDescription().getErrorMessage()%></span><p>
<div class="input-group">
  <span class="input-group-addon" id="sizing-addon2">Müügihind</span>
  <input type="text" class="form-control" nname="price" value="<%=model.getPrice().getAttributeValue()%>"  aria-describedby="sizing-addon2">
</div><span><%=model.getPrice().getErrorMessage()%></span><p>
------------------------ attribuudid ------------------------
<%
    for(Long key : model.getAttributes().keySet()){
		AttributeModel attributeModel = model.getAttributes().get(key);
		out.println("<tr><td>"+attributeModel.getAttributeName()+"</td><td>");
		out.println("<input type='text' name='"+key+"' placeholder='"
		+attributeModel.getAttributeName()+"' value='"+attributeModel.getAttributeValue()+"'/>"+
		"<input type='hidden' name='"+key+"'value='"+attributeModel.getAttributeName()+"' />"
		+"<span>"+attributeModel.getErrorMessage()+"</span></td></tr>");
    }
	out.println("<tr><td>Toote tüüp</td><td>"+model.getType()+
	    "<input type='hidden' name='type' value='"+model.getType()+"' />"+
	    "<input type='hidden' name='itemType' value='"+model.getItemType()+"'</td><tr>");
%>
<tr><td><input type="submit" class="btn btn-default" value="Lisa uus toode" /></td></tr>
<%} %>
</table>
</form>
</body>
</html>