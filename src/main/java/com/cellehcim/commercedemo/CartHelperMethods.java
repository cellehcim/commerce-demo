package com.cellehcim.commercedemo;

import java.util.ArrayList;
import java.util.List;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.LineItemDraft;

import io.github.cdimascio.dotenv.Dotenv;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;

public class CartHelperMethods {

    private static final ServiceRegion COMMERCETOOLS_REGION = ServiceRegion.AWS_US_EAST_2;

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

    public static List<LineItemDraft> createLineItems(String[] lineItemProductSkus) {
        List<LineItemDraft> lineItemArrayList = new ArrayList<LineItemDraft>();

        for (String sku : lineItemProductSkus) {
            LineItemDraft lineItem = LineItemDraft.builder().sku(sku).build();
            lineItemArrayList.add(lineItem);
        }

        return lineItemArrayList;
    }

    /**
     * public static Cart createCartDraftObject(ProjectApiRoot apiRoot, CartDetails cartDetails) {

    }*/

    public static Cart createCartObject(ProjectApiRoot apiRoot, CartDraft cartDraft) {
        return apiRoot.carts().post(cartDraft).executeBlocking().getBody();
    }
}
