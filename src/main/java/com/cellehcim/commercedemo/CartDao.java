package com.cellehcim.commercedemo;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import com.commercetools.api.models.cart.LineItemDraft;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class CartDao {

    public Cart findCartById(String cartId) {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();

        return apiRoot.carts().withId(cartId).get().executeBlocking().getBody();
    }

    public Cart createEmptyCart(CartDetails cartDetails) {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails);

        return createCartObject(apiRoot, newCartDraft);
     }

    public Cart createCartWithLineItems(List<LineItemDraft> lineItemArrayList, CartDetails cartDetails) throws RuntimeException {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails, lineItemArrayList);

        return createCartObject(apiRoot, newCartDraft);
    }

    public static Cart createCartObject(ProjectApiRoot apiRoot, CartDraft cartDraft) {
        return apiRoot.carts().post(cartDraft).executeBlocking().getBody();
    }
}
