package com.cellehcim.commercedemo;

import java.util.ArrayList;
import java.util.List;

import com.commercetools.api.models.cart.LineItemDraft;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CartDetails {

    @NotNull(message = "The country code must be included as a two-digit ISO 3166-1 alpha-2 country code")
    @NotBlank(message = "The country code must be included as a two-digit ISO 3166-1 alpha-2 country code")
    @Size(min = 2, max = 2, message = "The country code must be exactly two digits")
    @JsonProperty("countryCode")
    private String countryCode;

    @NotNull(message = "The currency code must be included as a three-digit ISO 4217 currency code")
    @NotBlank(message = "The currency code must be included as a three-digit ISO 4217 currency code")
    @Size(min = 3, max = 3, message = "The currency code must be exactly three-digits")
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("lineItemDetails")
    private List<LineItemDraft> lineItems;

    public CartDetails() {}

    public CartDetails(String countryCode, String currency, @Valid List<LineItemDetail> lineItemDetails) {
        this.countryCode = countryCode.toUpperCase();
        this.currency = currency.toUpperCase();

        if (lineItemDetails != null) {
            this.lineItems = createLineItems(lineItemDetails);
        }
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

    public List<LineItemDraft> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemDraft> lineItems) {
        this.lineItems = lineItems;
    }

    /**
     * Creates a List of LineItemDraft objects from a list of the corresponding details (SKU and quantity).
     * @param lineItemDetail - a HashMap of correctly-entered character-for-character line item product SKUs, along with their (non-zero and non-negative) quantities.
     * @return a List of LineItemDraftObjects containing the SKUs from lineItemProductSkus.
     */

    public static List<LineItemDraft> createLineItems(List<LineItemDetail> lineItemDetails) {
        List<LineItemDraft> lineItemArrayList = new ArrayList<LineItemDraft>();

        for (LineItemDetail entry : lineItemDetails) {

            LineItemDraft lineItem = LineItemDraft.builder()
                .sku(entry.getSku()
                .toUpperCase())
                .quantity(entry.getQuantity())
                .build();
            lineItemArrayList.add(lineItem);
        }

        return lineItemArrayList;
    }
    
}
