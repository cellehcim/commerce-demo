package com.cellehcim.commercedemo;

import org.json.JSONObject;

public class TestHelperMethods {
    
    /**
     * Creates a JSON request body for a US-based shopping cart .
     * @return a JSON object with "USD" and "US" as the respective currency and country code.
     */
    public static JSONObject createEmptyAmericanCart() throws Exception {
        JSONObject requestParams = new JSONObject(); 
		requestParams.put("currency", "USD"); 
		requestParams.put("countryCode", "US"); 
        return requestParams;        
    }

    /**
     * Creates a sample JSON request object for a line item
     * @param sku - SKU of the line item object to create
     * @param quantity - quantity of the line item object
     * @return a JSON object containing the SKU and quantity needed to create a line item
     * @throws Exception
     */
    public static JSONObject createLineItemRequestObject(String sku, int quantity) throws Exception {
		JSONObject lineItem = new JSONObject();
		lineItem.put("sku", sku);
		lineItem.put("quantity", (long) quantity);
        return lineItem;
    }
}
