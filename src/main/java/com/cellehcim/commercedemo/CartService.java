package com.cellehcim.commercedemo;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.LineItemDraft;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;

@Service
public class CartService {
    
    private static final ServiceRegion COMMERCETOOLS_REGION = ServiceRegion.AWS_US_EAST_2;
    private static final String DEFAULT_CURRENCY = "USD";
    private static final String DEFAULT_COUNTRY = "US";

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
        Cart response = null;
        
        response = apiRoot.carts().withId(cartId).get().executeBlocking().getBody();

        return response;
    }

    public Cart createEmptyCart() {
        ProjectApiRoot apiRoot = createApiClient();
        CartDraft newCartDraft = CartDraft.builder().currency(DEFAULT_CURRENCY).build();

        return createCartObject(apiRoot, newCartDraft);
     }

    public Cart createEmptyCart(String currency) {
        ProjectApiRoot apiRoot = createApiClient();
        CartDraft newCartDraft = CartDraft.builder().currency(currency).build();
        return createCartObject(apiRoot, newCartDraft);
    }

    public Cart createCartObject(ProjectApiRoot apiRoot, CartDraft cartDraft) {
        return apiRoot.carts().post(cartDraft).executeBlocking().getBody();
    }

    public Cart createCartWithLineItems(String[] lineItemSkus) throws RuntimeException {
        ProjectApiRoot apiRoot = createApiClient();

        List<LineItemDraft> lineItemArrayList = createLineItems(lineItemSkus);

        if (lineItemArrayList.size() > 0) {
            CartDraft newCartDraft = CartDraft.builder().country(DEFAULT_COUNTRY).currency(DEFAULT_CURRENCY).lineItems(lineItemArrayList).build();
            return apiRoot.carts().post(newCartDraft).executeBlocking().getBody();
        } else {
            throw new RuntimeException("No valid SKUs!");
        }
    }

    public List<LineItemDraft> createLineItems(String[] lineItemProductSkus) {
        List<LineItemDraft> lineItemArrayList = new ArrayList<LineItemDraft>();

        for (String id : lineItemProductSkus) {
            LineItemDraft lineItem = LineItemDraft.builder().sku(id).build();
            lineItemArrayList.add(lineItem);
        }

        return lineItemArrayList;
    }

}
