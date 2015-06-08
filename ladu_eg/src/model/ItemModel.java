package model;

public class ItemModel {
	private int item_price_list;
	private int id;
	private String name;
	private Double discount_price;
	private Double sale_price;
	private Long discount_xtra;

	public int getItem_price_list() {
		return item_price_list;
	}
	public void setItem_price_list(int item_price_list) {
		this.item_price_list = item_price_list;
	}
	
	public int getId() {
		return id;
	}
	public Double getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSale_price() {
		return sale_price;
	}
	public void setSale_price(Double sale_price) {
		this.sale_price = sale_price;
	}
	public Long getDiscount_xtra() {
		return discount_xtra;
	}
	public void setDiscount_xtra(Long discount_xtra) {
		this.discount_xtra = discount_xtra;
	}
	
	

}
