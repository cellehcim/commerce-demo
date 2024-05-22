package com.cellehcim.commercedemo;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.cart.CartDraft;

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
     * Creates an empty cart draft object.
     * @param apiRoot - the ProjectApiRoot whose credentials we want to use for building the cart draft.
     * @param cartDetails - the cart detail information that we want to create a cart draft out of.
     * @return a cart draft object with the information specified in the CartDetails object.
     */

    public static CartDraft createCartDraftObject(ProjectApiRoot apiRoot, CartDetails cartDetails, boolean hasLineItems) {

        if (hasLineItems) {
            return CartDraft.builder()
                .country(cartDetails.getCountryCode())
                .currency(cartDetails.getCurrency())
                .lineItems(cartDetails.getLineItems())
                .build();
        } else {
            return CartDraft.builder()
                .country(cartDetails.getCountryCode())
                .currency(cartDetails.getCurrency())
                .build();
        }
    }
}
