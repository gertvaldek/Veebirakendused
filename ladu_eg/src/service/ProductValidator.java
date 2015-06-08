package service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import db.TypeAttribute;

import util.DBUtil;
import model.AttributeModel;
import model.ProductModel;

public class ProductValidator {
    
    private boolean isValid;
    
    public ProductValidator() {
	isValid = true;
    }
    
    private void validate(boolean result){
	if(isValid == true){
	    isValid = result;
	}
    }

    public boolean validateProductModel(ProductModel model) {
	DBUtil dbUtil = new DBUtil();
	validate(checkEmpty(model.getName()));
	validate(checkEmpty(model.getDescription()));
	boolean priceNotEmpty = checkEmpty(model.getPrice());
	if (!priceNotEmpty) {
	    validate(false);
	} else {
	    validate(checkNumeric(model.getPrice()));
	}
	if (StringUtils.isNotBlank(model.getItemType())) {
	    // attribuutide definitsioonide küsimine andmebaasist
	    List<TypeAttribute> attributeList = dbUtil.getTypeAttributesByItemType(Integer.parseInt(model.getItemType()));
	    Map<Long, TypeAttribute> attributeMap = new HashMap<Long, TypeAttribute>();
	    for (TypeAttribute at : attributeList) {
		attributeMap.put(at.getTypeAttribute(), at);
	    }
	    if (model.getAttributes() != null && model.getAttributes().size() > 0) {
		// käime läbi kõik sisestatud attribuudid
		for (Long currentId : model.getAttributes().keySet()) {
		    if (attributeMap.containsKey(currentId)) {
			TypeAttribute attributeDefinition = attributeMap.get(currentId);
			AttributeModel insertedAttribute = model.getAttributes().get(currentId);
			// kas on kohustuslik
			if (StringUtils.equalsIgnoreCase("Y", attributeDefinition.getRequired())) {
			    if (attributeDefinition.getItemAttributeType().getDataType().equals(1L)) {
				validate(checkEmpty(insertedAttribute));
			    } else if (attributeDefinition.getItemAttributeType().getDataType().equals(2L)) {
				boolean numericNotEmpty = checkEmpty(insertedAttribute);
				if (!numericNotEmpty) {
				    validate(false);
				} else {
				    validate(checkNumeric(insertedAttribute));
				}
			    }			    
			} else {
			    if (attributeDefinition.getItemAttributeType().getDataType().equals(2L)) {
				validate(checkNumeric(insertedAttribute));
			    }
			}
		    }
		}
	    }
	}

	return isValid;
    }

    /**
     * kontrollib, kas AttributeModel-is on väätus olemas ning kui pole siis
     * sisestab mudelisse errori teate
     * 
     * @param attribute
     *            - kontrollitav AttributeModel
     * @return
     */
    private boolean checkEmpty(AttributeModel attribute) {
	if (StringUtils.isBlank(attribute.getAttributeValue())) {
	    attribute.setErrorMessage("Väli peab olema täidetud");
	    return false;
	}
	return true;
    }

    /**
     * Kontrollib, kas sisestatud väärtus oleks number
     * 
     * @param attribute
     *            - kontrollitav AttributeModel
     * @return
     */
    private boolean checkNumeric(AttributeModel attribute) {
	if(StringUtils.isBlank(attribute.getAttributeValue())){
	    return true;
	}
	try {
	    String price = attribute.getAttributeValue();
	    price = price.replace(",", ".");
	    new BigDecimal(price);
	    attribute.setAttributeValue(price);
	    return true;
	} catch (Exception e) {
	    attribute.setErrorMessage("Peab olema number");
	    return false;
	}
    }

}
