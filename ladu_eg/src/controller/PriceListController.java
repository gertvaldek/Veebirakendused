package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CustomerModel;
import model.ItemModel;
import model.PriceListForm;

import dao.PriceListDAO;
import db.Customer;
import db.PriceList;
import db.UserAccount;

/**
 * Servlet implementation class PriceListController
 */
@WebServlet("/pricelist")
public class PriceListController extends BaseController {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PriceListController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doOnGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PriceListDAO dao = new PriceListDAO();
		RequestDispatcher view;
		if (request.getParameter("action") != null){
			view = doActions(request, dao);
		}
		if (request.getParameter("id") != null){
			view = changePriceListDetailsOrCreateNewPriceList(request, dao);

		}else{
			request.setAttribute("pricelistElements", dao.findAll());
			view = request.getRequestDispatcher("/pricelists.jsp");
		}
		request.setCharacterEncoding("UTF-8");
		view.forward(request, response);
	}

	/**
	 * meetod, mis otsustab, kas soovitakse teha tegevusi pricelists või pricelist-iga seoses
	 * @param request
	 * @param dao
	 * @return view
	 */
	private RequestDispatcher doActions(HttpServletRequest request, PriceListDAO dao) {
		RequestDispatcher view;
		String action = request.getParameter("action");
		if (action.equals("new") || action.equals("update") || action.equals("delete")){
			view = request.getRequestDispatcher("/pricelists.jsp");
			priceListsActions(action,request,dao);	
		}else{
			view = request.getRequestDispatcher("/pricelist.jsp");
			priceListActions(action, request, dao);
		}
		return view;
	}

	/**
	 * meetod, mis tegeleb hinnakirja muutmise lehel olevate actionitega: klientide ja toodete haldusega
	 * @param action
	 * @param request
	 * @param dao
	 */
	private void priceListActions(String action, HttpServletRequest request, PriceListDAO dao) {
		if (action.equals("searchcustomer")){
			List<CustomerModel> searchcustomers = dao.searchCustomer(request.getParameter("customer"));
			request.setAttribute("searchcustomers",searchcustomers);
		}
		if (action.equals("searchitem")){
			List<ItemModel> searchitems = dao.searchItem(request.getParameter("item"));
			request.setAttribute("searchitems",searchitems);
		}
		if (action.equals("addcustomer")){
			dao.addCustomer(Integer.parseInt(request.getParameter("customer")),Integer.parseInt(request.getParameter("id")));
		}
		if (action.equals("additem")){
			dao.addItem(Integer.parseInt(request.getParameter("item")),Integer.parseInt(request.getParameter("id")));
		}
		if (action.equals("deletecustomer")){
			dao.deleteCustomer(Integer.parseInt(request.getParameter("customer")),Integer.parseInt(request.getParameter("id")));
		}
		if (action.equals("deleteitem")){
			dao.deleteItem(Integer.parseInt(request.getParameter("item")),Integer.parseInt(request.getParameter("id")));
		}
		if (action.equals("changediscount")){
			dao.changeDiscount(Integer.parseInt(request.getParameter("item")),Integer.parseInt(request.getParameter("id")),Long.parseLong(request.getParameter("discount")));
		}
	}

	/**
	 * meetod hinnakirja muutmiseks, uue loomiseks ja vana kustutamiseks
	 * @param action
	 * @param request
	 * @param dao
	 */
	private void priceListsActions(String action, HttpServletRequest request, PriceListDAO dao) {
		PriceList priceList = null;
		if (action.equals("delete")){
			dao.deletePriceList(Integer.parseInt(request.getParameter("uid")));
			return;
		}
		try {
			priceList = getPriceListFromRequest(request).convertToPriceList();
		} catch (ParseException e) {
			System.out.println("PriceListDAO.priceListAction() : "+e.getMessage());
		}
		if (action.equals("new")){
			// uue loomisel on tarvis ka tootaja id-d, kes hinnakirja l6i
			UserAccount user = (UserAccount) request.getSession().getAttribute("user");
			dao.createNewPriceList(priceList,user.getEmployeeBySubjectFk().getEmployee());	
		}
		if (action.equals("update")){
			dao.updatePriceList(priceList);	
		}
	}

	/**
	 * meetod, mis teeb kindlaks, kas soovitakse luua uus hinnakiri v6i kuvada hinnakirja muutmise leht
	 * @param request
	 * @param dao
	 * @return view
	 */
	private RequestDispatcher changePriceListDetailsOrCreateNewPriceList(HttpServletRequest request, PriceListDAO dao) {
		RequestDispatcher view;
		if(request.getParameter("id").equals("new")){
			view = request.getRequestDispatcher("/newpricelist.jsp");
		}else{
			changeDetailsPage(request, dao);
			view = request.getRequestDispatcher("/pricelist.jsp");
		}
		return view;
	}

	/**
	 * meetid, mis m22rab hinnakirja muutmise lehe andmed pöördudes DAO poole
	 * @param request
	 * @param dao
	 */
	private void changeDetailsPage(HttpServletRequest request, PriceListDAO dao) {
		int price_list_id = Integer.parseInt(request.getParameter("id"));
		PriceListForm pricelist = dao.findById(price_list_id).convertToPriceListForm();
		request.setAttribute("pricelist",pricelist);
		List<String> list = dao.findOtherStatusTypes(pricelist.getPriceListStatusType());
		request.setAttribute("otherstatus", list);
		List<CustomerModel> customers = dao.findCustomersById(price_list_id);
		request.setAttribute("customers", customers);
		List<ItemModel> items = dao.findItemsById(price_list_id);
		request.setAttribute("items", items);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doOnPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * meetod, mis paneb requesti PriceListFormi kokku
	 * @param request
	 * @return priceListForm
	 */
	private PriceListForm getPriceListFromRequest(HttpServletRequest request) {
		PriceListDAO dao = new PriceListDAO();
		PriceListForm priceListForm = new PriceListForm(); 
		if(request.getParameter("id")!=null){
			priceListForm.setId(request.getParameter("id"));
		}
		priceListForm.setPriceListStatusType(""+dao.findStatusType(request.getParameter("status")).getPriceListStatusType());
		priceListForm.setDefaultDiscountXtra(request.getParameter("discount"));
		priceListForm.setDateFrom(request.getParameter("date_from"));
		priceListForm.setDateTo(request.getParameter("date_to"));
		priceListForm.setNote(request.getParameter("note"));
		return priceListForm;
	}

}
