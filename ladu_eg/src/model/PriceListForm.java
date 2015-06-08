package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.PriceListDAO;
import db.Employee;
import db.PriceList;
import db.PriceListStatusType;

public class PriceListForm {
	private String id;
	private String priceListStatusType;
	private String defaultDiscountXtra;
	private String dateFrom;
	private String dateTo;
	private String note;

	public PriceListForm() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPriceListStatusType() {
		return priceListStatusType;
	}

	public void setPriceListStatusType(String priceListStatusType) {
		this.priceListStatusType = priceListStatusType;
	}

	public String getDefaultDiscountXtra() {
		return defaultDiscountXtra;
	}

	public void setDefaultDiscountXtra(String defaultDiscountXtra) {
		this.defaultDiscountXtra = defaultDiscountXtra;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public PriceList convertToPriceList() throws ParseException{
		PriceList p = new PriceList();
		PriceListDAO dao = new PriceListDAO();
		if(id!=null){
		p.setPriceList(Long.parseLong(id));
		}
		p.setPriceListStatusType(dao.findStatusType(Integer.parseInt(priceListStatusType)));
		p.setDefaultDiscountXtra(Long.parseLong(defaultDiscountXtra));
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		p.setDateFrom(df.parse(dateFrom));
		p.setDateTo(df.parse(dateTo));
		p.setNote(note);
		return p;
	}
	
}
