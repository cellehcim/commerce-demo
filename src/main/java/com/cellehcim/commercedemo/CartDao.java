package com.cellehcim.commercedemo;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;

@Component
public class CartDao {

    private static final ServiceRegion COMMERCETOOLS_REGION = ServiceRegion.AWS_US_EAST_2;
    private static final String DEFAULT_CURRENCY = "USD";

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

    public Cart findCartById(String cartId) {
        ProjectApiRoot apiRoot = createApiClient();
        Cart cartToQuery = apiRoot.carts().withId(cartId).get().executeBlocking().getBody();
        return cartToQuery;
    }

    public Cart createCart() {
        ProjectApiRoot apiRoot = createApiClient();
        CartDraft newCartDraft = CartDraft.builder().currency(DEFAULT_CURRENCY).build();

        return createCartObject(apiRoot, newCartDraft);
     }

    public Cart createCart(String currency) {
        ProjectApiRoot apiRoot = createApiClient();
        CartDraft newCartDraft = CartDraft.builder().currency(currency).build();
        return createCartObject(apiRoot, newCartDraft);
    }

    public Cart createCartObject(ProjectApiRoot apiRoot, CartDraft cartDraft) {
        return apiRoot.carts().post(cartDraft).executeBlocking().getBody();
    }
}
