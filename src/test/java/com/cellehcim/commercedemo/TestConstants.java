package com.cellehcim.commercedemo;

public class TestConstants {
    final static String ENDPOINT_URL = "http://localhost:8080/api/v1/carts";

    final static String VALID_CART_ID = "0cf86224-25f2-4e30-af5c-d91602a4f6bd";
    final static String INVALID_CART_ID = "0cf86224-25f2-4e30-af5c-d91602a4f6b";

    final static String ASHEN_RUG_SKU = "ARG-56";
    final static String ART_DECO_COFFEE_TABLE_SKU = "ADCT-01";
    
    final static String ID_JSON_PATH_VALUE = "$.id";
    final static String LINE_ITEMS_JSON_PATH_VALUE = "$.lineItems";
    final static String LINE_ITEMS_LENGTH_JSON_PATH_VALUE = "$.lineItems.length()";
    final static String COUNTRY_JSON_PATH_VALUE = "$.country";
    final static String CURRENCY_CODE_JSON_PATH_VALUE = "$.totalPrice.currencyCode";
}
