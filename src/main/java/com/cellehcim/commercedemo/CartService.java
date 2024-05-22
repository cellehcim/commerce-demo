package com.cellehcim.commercedemo;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.LineItemDraft;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    
    private static final String DEFAULT_CURRENCY = "USD";
    private static final String DEFAULT_COUNTRY = "US";

    public Cart findCartById(String cartId) {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        Cart response = null;
        
        response = apiRoot.carts().withId(cartId).get().executeBlocking().getBody();

        return response;
    }

    public Cart createCart(CartDetails cartDetails) {
        List<LineItemDraft> lineItemArrayList = CartHelperMethods.createLineItems(cartDetails.getLineItemSkus());

        if (lineItemArrayList != null) {
            return createCartWithLineItems(lineItemArrayList);
        } else {
            return createEmptyCart();
        }
    }

    public Cart createEmptyCart() {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartDraft.builder().currency(DEFAULT_CURRENCY).build();

        return createCartObject(apiRoot, newCartDraft);
     }

    public Cart createEmptyCart(String currency) {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartDraft.builder().currency(currency).build();
        return createCartObject(apiRoot, newCartDraft);
    }

    public Cart createCartObject(ProjectApiRoot apiRoot, CartDraft cartDraft) {
        return apiRoot.carts().post(cartDraft).executeBlocking().getBody();
    }

    public Cart createCartWithLineItems(List<LineItemDraft> lineItemArrayList) throws RuntimeException {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();

        CartDraft newCartDraft = CartDraft.builder().country(DEFAULT_COUNTRY).currency(DEFAULT_CURRENCY).lineItems(lineItemArrayList).build();
        return apiRoot.carts().post(newCartDraft).executeBlocking().getBody();
    }
}
