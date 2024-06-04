package com.cellehcim.commercedemo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LineItemDetail {
    @NotNull(message = "The SKU must be included")
    @NotBlank(message = "The SKU must be included")
    @JsonProperty("sku")
    private String sku;

    @NotNull(message = "The quantity must be included")
    @NotBlank(message = "The quantity must be included")
    @Min(message = "Line item quantity must be greater than zero", value = 1)
    @JsonProperty("quantity")
    private Long quantity;

    public LineItemDetail() {}

    public LineItemDetail(String sku, Long quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    
}

