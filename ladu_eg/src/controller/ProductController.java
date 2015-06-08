package controller;

import db.Item;
import db.ItemAttribute;
import db.ItemAttributeType;
import db.ItemType;
import db.TypeAttribute;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AttributeModel;
import model.ProductModel;
import org.apache.commons.lang3.StringUtils;
import service.ProductValidator;
import util.DBUtil;
import util.FormUtil;

@WebServlet({"/product"})
public class ProductController
  extends BaseController
{
  private static final long serialVersionUID = 1L;
  
  protected void doOnPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    RequestDispatcher view = request.getRequestDispatcher("/productEdit.jsp");
    Map<String, String[]> parameterMap = request.getParameterMap();
    if (parameterMap.containsKey("id"))
    {
      DBUtil dbUtil = new DBUtil();
      ProductModel m = FormUtil.getProductFromParameterMap(parameterMap);
      if (parameterMap.containsKey("delete"))
      {
        Item item = dbUtil.getItemById(Integer.parseInt(request.getParameter("id")));
        if (item != null)
        {
          StringBuilder errors = new StringBuilder();
          if ((item.getItemActions() != null) && (item.getItemActions().size() > 0))
          {
            errors.append("Kustutamine ebaõnnestus, sest tootega on seotud laotoiminguid!<br>");
          }
          else if ((item.getItemPriceLists() != null) && (item.getItemPriceLists().size() > 0))
          {
            errors.append("Kustutamine ebaõnnestus, sest tootega on seotud hinnakirjasid!<br>");
          }
          else if ((item.getItemStores() != null) && (item.getItemStores().size() > 0))
          {
            errors.append("Kustutamine ebaõnnestus, sest tooted on juba laos arvel!<br>");
          }
          else
          {
            dbUtil.deleteItem(Long.valueOf(item.getItem()));
            response.sendRedirect(request.getContextPath());
            return;
          }
          request.setAttribute("error", errors.toString());
        }
      }
      else
      {
        ProductValidator validator = new ProductValidator();
        boolean isValid = validator.validateProductModel(m);
        if (isValid)
        {
          Item item = dbUtil.updateItem(m, new Long(((String[])parameterMap.get("id"))[0]));
          if (item != null)
          {
            response.sendRedirect(request.getContextPath() + "/product?id=" + request.getParameter("id"));
            return;
          }
        }
      }
      request.setAttribute("productModel", m);
    }
    view.forward(request, response);
  }
  
  protected void doOnGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    RequestDispatcher view = request.getRequestDispatcher("/productEdit.jsp");
    if ((request.getParameter("id") != null) && (StringUtils.isNumeric(request.getParameter("id"))))
    {
      DBUtil dbUtil = new DBUtil();
      
      Item item = dbUtil.getItemById(Integer.parseInt(request.getParameter("id")));
      if (item != null)
      {
        ProductModel model = new ProductModel();
        model.getName().setAttributeValue(item.getName());
        model.getDescription().setAttributeValue(item.getDescription());
        model.getPrice().setAttributeValue(item.getSalePrice().toString());
        Set<TypeAttribute> typeAttributes = item.getItemType().getTypeAttributes();
        Map<Long, TypeAttribute> typeAttributeIdMap = new HashMap();
        for (TypeAttribute t : typeAttributes) {
          typeAttributeIdMap.put(Long.valueOf(t.getItemAttributeType().getItemAttributeType()), t);
        }
        for (ItemAttribute attribute : item.getItemAttributes())
        {
          AttributeModel attributeModel = new AttributeModel();
          attributeModel.setAttributeId(Long.valueOf(attribute.getItemAttribute()));
          attributeModel.setAttributeName(attribute.getItemAttributeType().getTypeName());
          if (attribute.getDataType().equals(Long.valueOf(1L))) {
            attributeModel.setAttributeValue(attribute.getValueText());
          } else if (attribute.getDataType().equals(Long.valueOf(2L))) {
            attributeModel.setAttributeValue(attribute.getValueNumber().toString());
          }
          Long typeId = Long.valueOf(((TypeAttribute)typeAttributeIdMap.get(Long.valueOf(attribute.getItemAttributeType().getItemAttributeType()))).getTypeAttribute());
          typeAttributeIdMap.remove(Long.valueOf(attribute.getItemAttributeType().getItemAttributeType()));
          model.getAttributes().put(typeId, attributeModel);
        }
        for (TypeAttribute t : typeAttributeIdMap.values())
        {
          AttributeModel attibute = new AttributeModel();
          attibute.setAttributeName(t.getItemAttributeType().getTypeName());
          model.getAttributes().put(Long.valueOf(t.getTypeAttribute()), attibute);
        }
        ItemType type = item.getItemType();
        model.setType(type.getTypeName());
        model.setItemType(String.valueOf(type.getItemType()));
        
        request.setAttribute("productModel", model);
      }
    }
    view.forward(request, response);
  }
}
