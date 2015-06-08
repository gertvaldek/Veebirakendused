package model;

public class AttributeModel {
    
    private String attributeName ="";
    private String attributeValue = "";
    private String errorMessage = "";
    private Long attributeId;
    public String getAttributeName() {
        return attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    public String getAttributeValue() {
        return attributeValue;
    }
    public void setAttributeValue(String attributeValue) {
	if(attributeValue != null){
	    this.attributeValue = attributeValue; 
	}     
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public Long getAttributeId() {
	return attributeId;
    }
    public void setAttributeId(Long attributeId) {
	this.attributeId = attributeId;
    }
    

}
