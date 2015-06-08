package controller;

import db.Item;
import db.ItemAttributeType;
import db.ItemType;
import db.TypeAttribute;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AttributeModel;
import model.ProductModel;
import service.ProductValidator;
import util.DBUtil;
import util.FormUtil;

@WebServlet({"/insert"})
public class InsertController
  extends BaseController
  implements Servlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doOnPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    RequestDispatcher view = request.getRequestDispatcher("/insert.jsp");
    Map<String, String[]> parameterMap = request.getParameterMap();
    ProductModel m = FormUtil.getProductFromParameterMap(parameterMap);
    ProductValidator validator = new ProductValidator();
    boolean isValid = validator.validateProductModel(m);
    if (isValid)
    {
      DBUtil dbUtil = new DBUtil();
      Item item = dbUtil.saveItem(m);
      if (item != null)
      {
        response.sendRedirect(request.getContextPath() + "/product?id=" + item.getItem());
        return;
      }
    }
    request.setAttribute("productModel", m);
    view.forward(request, response);
  }
  
  protected void doOnGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    if (request.getParameter("catalog") != null)
    {
      RequestDispatcher view = request.getRequestDispatcher("/insert.jsp");
      Integer id = Integer.valueOf(Integer.parseInt(request.getParameter("catalog")));
      DBUtil m = new DBUtil();
      List<TypeAttribute> itemAttributes = m.getTypeAttributesByItemType(id.intValue());
      ProductModel model = new ProductModel();
      ItemType type = m.getItemTypeById(id.intValue());
      model.setType(type.getTypeName());
      model.setItemType(id.toString());
      for (TypeAttribute attribute : itemAttributes)
      {
        AttributeModel attibute = new AttributeModel();
        attibute.setAttributeName(attribute.getItemAttributeType().getTypeName());
        model.getAttributes().put(Long.valueOf(attribute.getTypeAttribute()), attibute);
      }
      request.setAttribute("productModel", model);
      view.forward(request, response);
    }
  }
}
