package model;

import java.util.HashMap;
import java.util.Map;

public class ProductModel {

    private AttributeModel name = new AttributeModel();
    private AttributeModel description = new AttributeModel();
    private AttributeModel price = new AttributeModel();
    private String type;
    private String itemType;
    private Map<Long, AttributeModel> attributes = new HashMap<Long, AttributeModel>();

    public AttributeModel getName() {
        return name;
    }
    public void setName(AttributeModel name) {
        this.name = name;
    }
    public AttributeModel getDescription() {
        return description;
    }
    public void setDescription(AttributeModel description) {
        this.description = description;
    }
    public AttributeModel getPrice() {
        return price;
    }
    public void setPrice(AttributeModel price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getItemType() {
	return itemType;
    }
    public void setItemType(String itemType) {
	this.itemType = itemType;
    }
    public Map<Long, AttributeModel> getAttributes() {
	return attributes;
    }
    public void setAttributes(Map<Long, AttributeModel> attributes) {
	this.attributes = attributes;
    }
       
}
