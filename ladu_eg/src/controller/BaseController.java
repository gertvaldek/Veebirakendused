package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class BaseController
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    if (req.getSession().getAttribute("user") == null) {
      resp.sendRedirect("/ladu_eg/login");
    } else {
      try
      {
        doOnGet(req, resp);
      }
      catch (Exception e)
      {
        resp.sendRedirect("/viga");
        e.printStackTrace();
      }
    }
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    if (req.getSession().getAttribute("user") == null) {
      resp.sendRedirect("/ladu_eg/login");
    } else {
      try
      {
        doOnPost(req, resp);
      }
      catch (Exception e)
      {
        resp.sendRedirect("/viga");
        e.printStackTrace();
      }
    }
  }
  
  protected abstract void doOnPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException, SQLException;
  
  protected abstract void doOnGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException, SQLException;
}
