package com.cellehcim.commercedemo;

import java.util.ArrayList;
import java.util.List;

import com.commercetools.api.models.cart.LineItemDraft;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CartDetails {

    @NotNull(message = "The country code must be included as a two-digit ISO 3166-1 alpha-2 country code")
    @NotBlank(message = "The country code must be included as a two-digit ISO 3166-1 alpha-2 country code")
    @Size(min = 2, max = 2, message = "The country code must be exactly two digits")
    private String countryCode;

    @NotNull(message = "The currency code must be included as a three-digit ISO 4217 currency code")
    @NotBlank(message = "The currency code must be included as a three-digit ISO 4217 currency code")
    @Size(min = 3, max = 3, message = "The currency code must be exactly three-digits")
    private String currency;

    private String[] lineItemSkus;

    private List<LineItemDraft> lineItems;

    public CartDetails(String countryCode, String currency, String[] lineItemSkus) {
        this.countryCode = countryCode.toUpperCase();
        this.currency = currency.toUpperCase();
        this.lineItems = createLineItems(lineItemSkus);
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

    public List<LineItemDraft> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemDraft> lineItems) {
        this.lineItems = lineItems;
    }

    /**
     * Creates a List of LineItemDraft objects from a list of those objects' SKUs.
     * @param lineItemProductSkus - a list of correctly-entered character-for-character line item product SKUs.
     * @return a List of LineItemDraftObjects containing the SKUs from lineItemProductSkus.
     */

    public static List<LineItemDraft> createLineItems(String[] lineItemProductSkus) {
        List<LineItemDraft> lineItemArrayList = new ArrayList<LineItemDraft>();

        for (String sku : lineItemProductSkus) {
            LineItemDraft lineItem = LineItemDraft.builder().sku(sku.toUpperCase()).build();
            lineItemArrayList.add(lineItem);
        }

        return lineItemArrayList;
    }
    
}
