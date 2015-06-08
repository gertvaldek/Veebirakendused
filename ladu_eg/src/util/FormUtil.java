package util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import model.AttributeModel;
import model.ProductModel;
import model.SearchForm;

public class FormUtil {
    
    public static ProductModel getProductFromParameterMap(Map<String, String[]> parameterMap){
	ProductModel model = new ProductModel();
	model.getName().setAttributeValue(getValueFromMap("name", parameterMap));
	model.getDescription().setAttributeValue(getValueFromMap("description", parameterMap));
	model.getPrice().setAttributeValue(getValueFromMap("price", parameterMap));
	model.setType(getValueFromMap("type", parameterMap));
	model.setItemType(getValueFromMap("itemType", parameterMap));
	for(String key : parameterMap.keySet()){
	    String[] valueAndName = parameterMap.get(key);
	    if(StringUtils.isNumeric(key) && valueAndName.length > 1){
		AttributeModel a = new AttributeModel();
		a.setAttributeValue(valueAndName[0]);
		a.setAttributeName(valueAndName[1]);
		if(valueAndName.length>2 && !StringUtils.equalsIgnoreCase("null", valueAndName[2])){
		    a.setAttributeId(Long.parseLong(valueAndName[2]));
		}
		model.getAttributes().put(Long.parseLong(key), a);
	    }
	}
	return model;
    }
    
    public static SearchForm getSearchFormFromRequestMap(Map<String, String[]> parameterMap){
	SearchForm form = new SearchForm();
	form.setName(getValueFromMap("name", parameterMap));
	form.setDescription(getValueFromMap("description", parameterMap));
	form.setProducer(getValueFromMap("producer", parameterMap));
	form.setProducerCode(getValueFromMap("producer_code", parameterMap));
	form.setQuantity(getValueFromMap("quantity", parameterMap));
	form.setSalePrice(getValueFromMap("sale_price", parameterMap));
	form.setStorePrice(getValueFromMap("store_price", parameterMap));
	form.setAttribute(getValueFromMap("attribute", parameterMap));
	form.setType(getValueFromMap("type", parameterMap));
	for(String parameter : parameterMap.keySet()){
	    if(StringUtils.isNumeric(parameter)){
		AttributeModel attribute = new AttributeModel();
		String[] values = parameterMap.get(parameter);
		attribute.setAttributeValue(values[0]);
		attribute.setAttributeName(values[1]);
		form.getAttributes().put(Long.parseLong(parameter), attribute);
	    }
	}
	return form;
    }

    private static String getValueFromMap(String key, Map<String, String[]> parameterMap){
	if(key != null && parameterMap != null){
	    if(parameterMap.get(key) != null && parameterMap.get(key).length > 0){
		return parameterMap.get(key)[0];
	    }
	}
	return null;
    }
}
