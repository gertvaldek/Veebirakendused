package controller;

import db.ItemType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.DBUtil;
import view.DrawCatalog;

public class MainPageController
  extends BaseController
{
  private static final long serialVersionUID = 1L;
  
  protected void doOnGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    RequestDispatcher view = request.getRequestDispatcher("/main.jsp");
    request.setCharacterEncoding("UTF-8");
    
    int selectedCatalogId = 0;
    if (request.getParameter("catalog") != null)
    {
      String s = request.getParameter("catalog");
      selectedCatalogId = Integer.parseInt(s.trim());
      DBUtil m = new DBUtil();
      ItemType catalog = m.getItemTypeById(selectedCatalogId);
      Set<ItemType> subCatalogs = catalog.getItemTypes();
      if ((subCatalogs != null) && (subCatalogs.size() == 0)) {
        request.setAttribute("lastCatalog", m.getItemTypeById(selectedCatalogId).getTypeName());
      }
    }
    DrawCatalog drawCatalog = new DrawCatalog();
    String categoryTree = drawCatalog.DrawCatalogTree(selectedCatalogId);
    if (categoryTree != null) {
      request.setAttribute("categoryTree", categoryTree);
    }
    view.forward(request, response);
  }
  
  protected void doOnPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    request.getSession().invalidate();
    
    response.sendRedirect("/ladu_eg/login");
  }
}
