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
        Cart response = apiRoot.carts().withId(cartId).get().executeBlocking().getBody();

        return response;
    }

    public Cart createEmptyCart(CartDetails cartDetails) {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails);

        return CartHelperMethods.createCartObject(apiRoot, newCartDraft);
     }

    public Cart createCartWithLineItems(List<LineItemDraft> lineItemArrayList, CartDetails cartDetails) throws RuntimeException {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails, lineItemArrayList);

        return apiRoot.carts().post(newCartDraft).executeBlocking().getBody();
    }
}
