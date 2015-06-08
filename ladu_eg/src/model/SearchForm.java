package model;

import java.util.HashMap;
import java.util.Map;

public class SearchForm {
    private String name = "";
    private String description = "";
    private String producerCode = "";
    private String producer = "";
    private String quantity = "";
    private String salePrice = "";
    private String storePrice = "";
    private String attribute = "";
    private String type = "";
    private Map<Long, AttributeModel> attributes = new HashMap<Long, AttributeModel>();

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getProducerCode() {
	return producerCode;
    }

    public void setProducerCode(String producerCode) {
	this.producerCode = producerCode;
    }

    public String getProducer() {
	return producer;
    }

    public void setProducer(String producer) {
	this.producer = producer;
    }

    public String getQuantity() {
	return quantity;
    }

    public void setQuantity(String quantity) {
	this.quantity = quantity;
    }

    public String getSalePrice() {
	return salePrice;
    }

    public void setSalePrice(String salePrice) {
	if (salePrice != null) {
	    this.salePrice = salePrice.replace(",", ".");
	} else {
	    salePrice = "";
	}

    }

    public String getStorePrice() {
	return storePrice;
    }

    public void setStorePrice(String storePrice) {
	if (storePrice != null) {
	    this.storePrice = storePrice.replace(",", ".");
	} else {
	    this.storePrice = "";
	}
    }

    public String getAttribute() {
	return attribute;
    }

    public void setAttribute(String attribute) {
	this.attribute = attribute;
    }

    public Map<Long, AttributeModel> getAttributes() {
	return attributes;
    }

    public void setAttributes(Map<Long, AttributeModel> attributes) {
	this.attributes = attributes;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }  

}
