package db;

// Generated by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ItemPriceList generated by hbm2java
 */
@Entity
@Table(name = "item_price_list")
public class ItemPriceList implements java.io.Serializable {

	private long itemPriceList;
	private PriceList priceList;
	private Item item;
	private Long discountXtra;
	private BigDecimal salePrice;
	private Date created;

	public ItemPriceList() {
	}

	public ItemPriceList(long itemPriceList) {
		this.itemPriceList = itemPriceList;
	}

	public ItemPriceList(long itemPriceList, PriceList priceList, Item item,
			Long discountXtra, BigDecimal salePrice, Date created) {
		this.itemPriceList = itemPriceList;
		this.priceList = priceList;
		this.item = item;
		this.discountXtra = discountXtra;
		this.salePrice = salePrice;
		this.created = created;
	}

	@Id
	@Column(name = "item_price_list", unique = true, nullable = false, precision = 10, scale = 0)
	public long getItemPriceList() {
		return this.itemPriceList;
	}

	public void setItemPriceList(long itemPriceList) {
		this.itemPriceList = itemPriceList;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "price_list_fk")
	public PriceList getPriceList() {
		return this.priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_fk")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "discount_xtra", precision = 10, scale = 0)
	public Long getDiscountXtra() {
		return this.discountXtra;
	}

	public void setDiscountXtra(Long discountXtra) {
		this.discountXtra = discountXtra;
	}

	@Column(name = "sale_price", precision = 131089, scale = 0)
	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", length = 29)
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
