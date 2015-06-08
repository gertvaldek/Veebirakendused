package controller;

import db.Item;
import db.ItemAction;
import db.ItemStore;
import db.Store;
import db.UserAccount;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.WareHouseService;
import util.DBUtil;

@WebServlet({"/warehouse"})
public class WareHouseController
  extends BaseController
{
  private static final long serialVersionUID = 1L;
  
  protected void doOnGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    RequestDispatcher view = request.getRequestDispatcher("/warehouse.jsp");
    request.setCharacterEncoding("UTF-8");
    if (request.getParameter("item") != null)
    {
      Integer itemId = Integer.valueOf(Integer.parseInt(request.getParameter("item")));
      DBUtil dbUtil = new DBUtil();
      Item item = dbUtil.getItemById(itemId.intValue());
      List<Store> allStores = dbUtil.getAllWareHouses();
      if (item != null)
      {
        request.setAttribute("item", item);
        if (allStores != null) {
          request.setAttribute("allStores", allStores);
        }
      }
    }
    view.forward(request, response);
  }
  
  protected void doOnPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException
  {
    RequestDispatcher view = request.getRequestDispatcher("/warehouse.jsp");
    request.setCharacterEncoding("UTF-8");
    
    UserAccount user = (UserAccount)request.getSession().getAttribute("user");
    Map<String, String[]> paramtereMap = request.getParameterMap();
    DBUtil dbUtil = new DBUtil();
    WareHouseService wareHouseService = new WareHouseService();
    
    List<Store> allStores = dbUtil.getAllWareHouses();
    int itemId = Integer.parseInt(wareHouseService.getString(paramtereMap, "item_id"));
    Item item = dbUtil.getItemById(itemId);
    if ((request.getParameter("action").equals("register")) && (user != null) && (item != null) && (allStores != null))
    {
      ItemAction itemActionRegister = wareHouseService.createWareHouseRegisterItemAction(user, paramtereMap, allStores, item);
      if (itemActionRegister != null)
      {
        String itemCount = wareHouseService.getString(paramtereMap, "warehouse_register_quantity");
        String actionPrice = wareHouseService.getString(paramtereMap, "warehouse_register_price");
        dbUtil.updateItemPriceInWareHouse(item, Integer.parseInt(itemCount), Integer.parseInt(actionPrice));
        dbUtil.insertItemAction(itemActionRegister);
        request.setAttribute("register_successful", "Toote arvele võtmine õnnestus!");
      }
      request.setAttribute("item", item);
      request.setAttribute("allStores", allStores);
    }
    else if ((request.getParameter("action").equals("remove")) && (user != null) && (item != null) && (allStores != null))
    {
      String selectedStoreFrom = wareHouseService.getString(paramtereMap, "remove_from_store");
      long selectedStoreFromId = Integer.parseInt(selectedStoreFrom);
      
      String itemCountOnMove = wareHouseService.getString(paramtereMap, "warehouse_remove_quantity");
      BigDecimal itemCountOnMoveBigDecimal = new BigDecimal(itemCountOnMove);
      
      ItemStore itemStoreFrom = wareHouseService.getItemStore(item, allStores, selectedStoreFromId);
      if (itemStoreFrom.getItemCount() == null)
      {
        request.setAttribute("move_from_err", "Antud toodet selles laos pole!");
      }
      else if (itemCountOnMoveBigDecimal.compareTo(itemStoreFrom.getItemCount()) == 1)
      {
        request.setAttribute("move_from_err_counts", "Toodet pole laos piisavalt!");
      }
      else
      {
        itemStoreFrom.setItemCount(itemStoreFrom.getItemCount().subtract(itemCountOnMoveBigDecimal));
        
        dbUtil.updateItemStore(itemStoreFrom);
        ItemAction itemActionRemove = wareHouseService.createWareHouseRemoveItemAction(user, paramtereMap, allStores, item);
        if (itemActionRemove != null)
        {
          dbUtil.insertItemAction(itemActionRemove);
          request.setAttribute("remove_successful", "Toote eemaldamine õnnestus!");
        }
      }
      request.setAttribute("item", item);
      request.setAttribute("allStores", allStores);
    }
    else if ((request.getParameter("action").equals("move")) && (user != null) && (item != null) && (allStores != null))
    {
      String selectedStoreFrom = wareHouseService.getString(paramtereMap, "move_from_store");
      long selectedStoreFromId = Integer.parseInt(selectedStoreFrom);
      
      String selectedStoreTo = wareHouseService.getString(paramtereMap, "move_to_store");
      long selectedStoreToId = Integer.parseInt(selectedStoreTo);
      
      String itemCountOnMove = wareHouseService.getString(paramtereMap, "warehouse_move_quantity");
      BigDecimal itemCountOnMoveBigDecimal = new BigDecimal(itemCountOnMove);
      
      ItemStore itemStoreFrom = wareHouseService.getItemStore(item, allStores, selectedStoreFromId);
      ItemStore itemStoreTo = wareHouseService.getItemStore(item, allStores, selectedStoreToId);
      if (itemStoreFrom.getItemCount() == null)
      {
        request.setAttribute("move_from_err", "Antud toodet selles laos pole!");
      }
      else if (itemCountOnMoveBigDecimal.compareTo(itemStoreFrom.getItemCount()) == 1)
      {
        request.setAttribute("move_from_err_counts", "Toodet pole laos piisavalt!");
      }
      else if (itemStoreTo != null)
      {
        itemStoreFrom.setItemCount(itemStoreFrom.getItemCount().subtract(itemCountOnMoveBigDecimal));
        itemStoreTo.setItemCount(itemStoreTo.getItemCount().add(itemCountOnMoveBigDecimal));
        
        dbUtil.updateItemStore(itemStoreFrom);
        dbUtil.updateItemStore(itemStoreTo);
        
        ItemAction itemActionMove = wareHouseService.createWareHouseMoveItemAction(user, paramtereMap, allStores, item);
        if (itemActionMove != null)
        {
          dbUtil.insertItemAction(itemActionMove);
          request.setAttribute("move_successful", "Toote ladude vahel liigutamine õnnestus!");
        }
      }
      else
      {
        ItemStore newItemStore = new ItemStore();
        newItemStore.setItem(item);
        newItemStore.setItemCount(itemCountOnMoveBigDecimal);
        newItemStore.setStore(wareHouseService.getSelectedStore(allStores, selectedStoreToId));
        dbUtil.insertItemStore(newItemStore);
        
        ItemAction itemActionMove = wareHouseService.createWareHouseMoveItemAction(user, paramtereMap, allStores, item);
        if (itemActionMove != null)
        {
          dbUtil.insertItemAction(itemActionMove);
          request.setAttribute("move_successful", "Toote ladude vahel liigutamine õnnestus!");
        }
      }
      request.setAttribute("item", item);
      request.setAttribute("allStores", allStores);
    }
    else
    {
      request.setAttribute("parameter_needed", "Laotoimingute tegemiseks on parameeter action vajalik!");
    }
    view.forward(request, response);
  }
}
