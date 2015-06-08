package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import model.CustomerModel;
import model.ItemModel;


import db.Customer;
import db.Enterprise;
import db.Person;
import db.PriceList;
import db.PriceListStatusType;
import dao.dbconnection;

public class PriceListDAO {
	static Logger logger = Logger.getLogger(dbconnection.class);
	
	public List<PriceList> findAll(){
		List<PriceList> list = new LinkedList<PriceList>();
		ResultSet result = dbconnection.executeQuery("SELECT price_list, price_list_status_type_fk, default_discount_xtra, date_from, date_to, note FROM price_list ORDER BY price_list");
		if (result == null) {
			return null;
		}
		try {
			while (result.next()) {
				PriceList p = new PriceList();
				p.setPriceList(result.getLong("price_list"));
				p.setDefaultDiscountXtra(result.getLong("default_discount_xtra"));
				p.setPriceListStatusType(findStatusType(result.getInt("price_list_status_type_fk")));
				p.setDateFrom(result.getDate("date_from"));
				p.setDateTo(result.getDate("date_to"));
				p.setNote(result.getString("note"));
				list.add(p);
			}
			return list;
		} catch (SQLException e) {
			logger.error("PriceListDAO.findAll() : "+e.getMessage());
			return null;
		}	
	}

	public PriceListStatusType findStatusType(int status){
		ResultSet result = dbconnection.executeQuery("SELECT price_list_status_type, type_name FROM price_list_status_type WHERE price_list_status_type="+status);
		PriceListStatusType s= new PriceListStatusType();
		try {
			if (!result.next()) {
				return null;
			}
			else {
				s.setPriceListStatusType(result.getLong("price_list_status_type"));
				s.setTypeName(result.getString("type_name"));

			}
			return s;
		} catch (SQLException e) {
			logger.error("PriceListDAO.findStatusType() : "+e.getMessage());
			return null;
		}	
	}
	public PriceListStatusType findStatusType(String status){
		ResultSet result = dbconnection.executeQuery("SELECT price_list_status_type, type_name FROM price_list_status_type WHERE type_name='"+status+"'");
		PriceListStatusType s= new PriceListStatusType();
		try {
			if (!result.next()) {
				return null;
			}
			else {
				s.setPriceListStatusType(result.getLong("price_list_status_type"));
				s.setTypeName(result.getString("type_name"));

			}
			return s;
		} catch (SQLException e) {
			logger.error("PriceListDAO.findStatusType() : "+e.getMessage());
			return null;
		}	
	}

	public void createNewPriceList(PriceList priceList, int created_by) {
		if (priceList == null) {
			return;
		}
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO price_list(price_list_status_type_fk, default_discount_xtra, date_from, date_to, note, created_by, created)" +
							"VALUES (?,?,?,?,?,?,LOCALTIMESTAMP(0))");
			statement.setLong(1, priceList.getPriceListStatusType().getPriceListStatusType());
			statement.setLong(2, priceList.getDefaultDiscountXtra());
			statement.setDate(3, new java.sql.Date(priceList.getDateFrom().getTime()));
			statement.setDate(4, new java.sql.Date(priceList.getDateTo().getTime()));
			statement.setString(5, priceList.getNote());
			statement.setLong(6, created_by);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.createNewPriceList() : "+e.getMessage());
		}
	}

	public PriceList findById(int id){
		ResultSet result = dbconnection.executeQuery("SELECT * FROM price_list WHERE price_list=" + id);
		PriceList p = new PriceList();
		try {
			if (!result.next()) {
				return null;
			}
			else {
				p.setPriceList(result.getLong("price_list"));
				p.setDefaultDiscountXtra(result.getLong("default_discount_xtra"));
				p.setPriceListStatusType(findStatusType(result.getInt("price_list_status_type_fk")));
				p.setDateFrom(result.getDate("date_from"));
				p.setDateTo(result.getDate("date_to"));
				p.setNote(result.getString("note"));      
			}
			return p;
		} catch (SQLException e) {
			logger.error("PriceListDAO.findById() : "+e.getMessage());
			return null;
		}	
	}

	public void updatePriceList(PriceList priceList) {
		if (priceList == null) {
			return;
		}
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE price_list SET price_list_status_type_fk=?, default_discount_xtra=?, date_from=?, date_to=?, note=?, updated_by=?, updated=LOCALTIMESTAMP(0)" +
							"WHERE price_list=?");
			statement.setLong(1, priceList.getPriceListStatusType().getPriceListStatusType());
			statement.setLong(2, priceList.getDefaultDiscountXtra());
			statement.setDate(3, new java.sql.Date(priceList.getDateFrom().getTime()));
			statement.setDate(4, new java.sql.Date(priceList.getDateTo().getTime()));
			statement.setString(5, priceList.getNote());
			statement.setLong(6, 1); //sisse loginud kasutaja
			statement.setLong(7,priceList.getPriceList());
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.updatePriceList() : "+e.getMessage());
		}
	}

	public void deletePriceList(int id) {
		deletePriceListRelations(id);
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM price_list WHERE price_list="+id);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.deletePriceList() : "+e.getMessage());
		}
	}

	private void deletePriceListRelations(int id) {
		deleteAllPriceListCustomers(id);
		deleteAllPriceListItems(id);
	}

	private void deleteAllPriceListItems(int price_list) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM item_price_list WHERE price_list_fk="+price_list);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.deleteAllPriceListItems() : "+e.getMessage());
		}
	}

	private void deleteAllPriceListCustomers(int price_list) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM customer_price_list WHERE price_list_fk="+price_list);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.deleteAllPriceListCustomers() : "+e.getMessage());
		}
	}

	public List<String> findOtherStatusTypes(String status) {
		List<String> list = new LinkedList<String>();
		ResultSet result = dbconnection.executeQuery("SELECT DISTINCT type_name FROM price_list_status_type WHERE NOT type_name='"+status+"'");
		if (result == null) {
			return null;
		}
		try {
			while (result.next()) {
				String s = result.getString("type_name");
				list.add(s);
			}
			return list;
		} catch (SQLException e) {
			logger.error("PriceListDAO.findOtherStatusTypes() : "+e.getMessage());
			return null;
		}	
	}


	public List <CustomerModel> searchCustomer(String name) {
		List <CustomerModel> list = new LinkedList<CustomerModel>();
		ResultSet result = dbconnection.executeQuery("SELECT P.subject_id ,P.subject_name, P.subject_type FROM"+
		"(SELECT customer AS subject_id, 'isik'  AS subject_type, (first_name || ' ' || last_name) AS subject_name " +
		" FROM person INNER JOIN customer ON person=subject_fk WHERE subject_type_fk=1 AND UPPER(last_name) LIKE UPPER('"+name+"%')"+
			"UNION SELECT customer AS subject_id, 'ettevote'  AS subject_type, name AS subject_name FROM enterprise INNER JOIN "+
		"customer ON enterprise=subject_fk WHERE subject_type_fk=2 AND UPPER(name) LIKE UPPER('"+name+"%' )) AS P");

		if (result == null) {
			return null;			
		}
		try {
			while (result.next()) {
				CustomerModel c = new CustomerModel();
				c.setId(result.getInt("subject_id"));
				c.setName(result.getString("subject_name"));
				c.setType(result.getString("subject_type"));
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			logger.error("PriceListDAO.searchCustomer() : "+e.getMessage());
			return null;
		}
	}

	public List<CustomerModel> findCustomersById(int price_list) {
		List <CustomerModel> list = new LinkedList<CustomerModel>();
		ResultSet result = dbconnection.executeQuery("SELECT kood, klient FROM f_leia_kliendid("+price_list+")");	
		if (result == null) {
			return null;
		}
		try {
			while (result.next()) {
				CustomerModel c = new CustomerModel();
				c.setId(result.getInt("kood"));
				c.setName(result.getString("klient"));
				list.add(c);
			}
			return list;
		} catch (SQLException e) {
			logger.error("PriceListDAO.findCustomersById() : "+e.getMessage());
			return null;
		}
	}

	public void addCustomer(int customer, int price_list) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO customer_price_list(customer_fk, price_list_fk)" +
							"VALUES (?,?)");
			statement.setInt(1,customer);
			statement.setInt(2, price_list);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.addCustomer() : "+e.getMessage());
		}
	}

	public void deleteCustomer(int customer, int price_list) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM customer_price_list WHERE price_list_fk="+price_list+" AND customer_fk="+customer);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.deleteCustomer() : "+e.getMessage());
		}
	}

	public List<ItemModel> findItemsById(int price_list) {
		List <ItemModel> list = new LinkedList<ItemModel>();
		ResultSet result = dbconnection.executeQuery("SELECT P.item_price_list, I.item, I.name, I.sale_price, P.discount_xtra, P.sale_price AS price_list_sale_price FROM item AS I INNER JOIN item_price_list AS P ON I.item=P.item_fk WHERE P.price_list_fk="+price_list+" ORDER BY item");
		if (result == null) {
			return null;
		}
		try {
			while (result.next()) {
				ItemModel i = new ItemModel();
				i.setItem_price_list(result.getInt("item_price_list"));
				i.setId(result.getInt("item"));
				i.setName(result.getString("name"));
				i.setSale_price(result.getDouble("sale_price"));
				i.setDiscount_xtra(result.getLong("discount_xtra"));
				i.setDiscount_price(result.getDouble("price_list_sale_price"));
				list.add(i);
			}
			return list;
		} catch (SQLException e) {
			logger.error("PriceListDAO.findItemsById() : "+e.getMessage());
			return null;
		}
	}

	public List<ItemModel> searchItem(String itemName) {
		List <ItemModel> list = new LinkedList<ItemModel>();
		ResultSet result = dbconnection.executeQuery("SELECT item, name FROM item WHERE UPPER(name) LIKE UPPER('"+itemName+"%') ORDER BY item");

		if (result == null) {
			return null;
		}
		try {
			while (result.next()) {
				ItemModel i = new ItemModel();
				i.setId(result.getInt("item"));
				i.setName(result.getString("name"));
				list.add(i);
			}
			return list;
		} catch (SQLException e) {
			logger.error("PriceListDAO.searchItem() : "+e.getMessage());
			return null;
		}
	}

	public void deleteItem(int item, int price_list) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("DELETE FROM item_price_list WHERE price_list_fk="+price_list+" AND item_fk="+item);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.deleteItem() : "+e.getMessage());
		}
	}

	public void addItem(int item, int price_list) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO item_price_list (item_fk, price_list_fk, discount_xtra, created) VALUES (?,?, (SELECT default_discount_xtra FROM price_list WHERE price_list=?),LOCALTIMESTAMP(0))");
			statement.setInt(1,item);
			statement.setInt(2, price_list);
			statement.setInt(3, price_list);
			statement.execute();
			statement.close();
			connection.close();
			calculateSalePrice(item, price_list);
		} catch (SQLException e) {
			logger.error("PriceListDAO.addItem() : "+e.getMessage());
		}
	}

	private void calculateSalePrice(int item, int price_list) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE item_price_list SET sale_price=(SELECT round(I.sale_price+(IP.discount_xtra*I.sale_price/100),2) FROM item_price_list AS IP INNER JOIN item AS I ON I.item=IP.item_fk WHERE price_list_fk=? AND item_fk=? )"+
							"WHERE price_list_fk=? AND item_fk=?" );
			statement.setInt(1, price_list);
			statement.setInt(2, item);
			statement.setInt(3, price_list);
			statement.setInt(4, item);
			statement.execute();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("PriceListDAO.calculateSalePrice() : "+e.getMessage());
		}
	}

	public void changeDiscount(int item, int price_list, Long discount) {
		Connection connection = dbconnection.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE item_price_list SET discount_xtra=?" +
							"WHERE price_list_fk=? AND item_fk=?");
			statement.setLong(1, discount);
			statement.setInt(2, price_list);
			statement.setInt(3, item);
			statement.execute();
			statement.close();
			connection.close();
			calculateSalePrice(item, price_list);
		} catch (SQLException e) {
			logger.error("PriceListDAO.changeDiscount() : "+e.getMessage());
		}
	}
}