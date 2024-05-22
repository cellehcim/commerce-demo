package com.cellehcim.commercedemo;

import java.util.ArrayList;
import java.util.List;

import com.commercetools.api.models.cart.LineItemDraft;

public class CartDetails {

    private String countryCode;
    private String currency;
    private String[] lineItemSkus;

    public CartDetails() {}

    public CartDetails(String countryCode, String currency, String[] lineItemSkus) {
        this.countryCode = countryCode;
        this.currency = currency;
        this.lineItemSkus = lineItemSkus;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String[] getLineItemSkus() {
        return lineItemSkus;
    }

    public void setLineItemSkus(String[] lineItemSkus) {
        this.lineItemSkus = lineItemSkus;
    }
    
}
