package com.cellehcim.commercedemo;

import java.util.ArrayList;
import java.util.List;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.LineItemDraft;

import io.github.cdimascio.dotenv.Dotenv;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;

public class CartHelperMethods {

    private static final ServiceRegion COMMERCETOOLS_REGION = ServiceRegion.AWS_US_EAST_2;

    /**
     * Creates an API client for making GET/POST requests with.
     * @return a ProjectApiRoute with the client ID, client secret, and project key stored in some environmental variables.
     */

    public static ProjectApiRoot createApiClient() {
        Dotenv dotenv = Dotenv.load();

        final ProjectApiRoot apiRoot = ApiRootBuilder.of()
        .defaultClient(ClientCredentials.of()
                .withClientId(dotenv.get("CTP_CLIENT_ID"))
                .withClientSecret(dotenv.get("CTP_CLIENT_SECRET"))
                .build(),
        COMMERCETOOLS_REGION)
        .build(dotenv.get("CTP_PROJECT_KEY"));

        return apiRoot;
    }

    /**
     * Creates a List of LineItemDraft objects from a list of those objects' SKUs.
     * @param lineItemProductSkus - a list of correctly-entered character-for-character line item product SKUs.
     * @return a List of LineItemDraftObjects containing the SKUs from lineItemProductSkus.
     */

    public static List<LineItemDraft> createLineItems(String[] lineItemProductSkus) {
        List<LineItemDraft> lineItemArrayList = new ArrayList<LineItemDraft>();

        for (String sku : lineItemProductSkus) {
            LineItemDraft lineItem = LineItemDraft.builder().sku(sku).build();
            lineItemArrayList.add(lineItem);
        }

        return lineItemArrayList;
    }

    /**
     * Creates an empty cart draft object.
     * @param apiRoot - the ProjectApiRoot whose credentials we want to use for building the cart draft.
     * @param cartDetails - the cart detail information that we want to create a cart draft out of.
     * @return a cart draft object with the information specified in the CartDetails object.
     */

    public static CartDraft createCartDraftObject(ProjectApiRoot apiRoot, CartDetails cartDetails) {
        return CartDraft.builder().country(cartDetails.getCountryCode()).currency(cartDetails.getCurrency()).build();
    }

      /**
     * Creates a cart draft object with line items.
     * @param apiRoot - the ProjectApiRoot whose credentials we want to use for building the cart draft.
     * @param cartDetails - the cart detail information that we want to create a cart draft out of.
     * @param lineItemArrayList - the line items that we want to include in the cart draft.
     * @return a cart draft object with the information specified in the CartDetails object.
     */

    public static CartDraft createCartDraftObject(ProjectApiRoot apiRoot, CartDetails cartDetails, List<LineItemDraft> lineItemArrayList) {
        return CartDraft.builder().country(cartDetails.getCountryCode()).currency(cartDetails.getCurrency()).lineItems(lineItemArrayList).build();
    }
}
