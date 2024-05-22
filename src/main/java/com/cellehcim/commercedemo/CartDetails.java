package com.cellehcim.commercedemo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CartDetails {

    @NotNull(message = "The country code must be included as a two-digit ISO 3166-1 alpha-2 country code")
    @NotBlank(message = "The country code must be included as a two-digit ISO 3166-1 alpha-2 country code")
    @Size(min = 2, max = 2, message = "The country code must be exactly two digits")
    @Pattern(regexp = "^[A-Z]*$", message = "The country code must be uppercase")
    private String countryCode;

    @NotNull(message = "The currency code must be included as a three-digit ISO 4217 currency code")
    @NotBlank(message = "The currency code must be included as a three-digit ISO 4217 currency code")
    @Size(min = 3, max = 3, message = "The currency code must be exactly three-digits")
    @Pattern(regexp = "^[A-Z]*$", message = "The currency code must be uppercase")
    private String currency;

    private String[] lineItemSkus;

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
