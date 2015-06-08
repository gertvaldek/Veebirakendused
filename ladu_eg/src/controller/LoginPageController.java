package controller;

import db.UserAccount;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.LoginService;
import util.DBUtil;

@WebServlet({"/login"})
public class LoginPageController
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
    view.forward(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
    request.setCharacterEncoding("UTF-8");
    UserAccount userAccount = new UserAccount();
    LoginService loginService = new LoginService();
    DBUtil dbUtil = new DBUtil();
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");
    userAccount = dbUtil.getUserByUsername(userName);
    if (userAccount != null)
    {
      String hashedPassword = loginService.hashPassword(password);
      if (userAccount.getPassw().equals(hashedPassword))
      {
        request.getSession().setAttribute("user", userAccount);
        response.sendRedirect("/ladu_eg/");
        return;
      }
      request.setAttribute("wrongpass", "Vale parool!");
    }
    else
    {
      request.setAttribute("wronguser", "Vale kasutaja!");
    }
    view.forward(request, response);
  }
}
