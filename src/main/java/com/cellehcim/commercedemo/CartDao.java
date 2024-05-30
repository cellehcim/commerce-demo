package com.cellehcim.commercedemo;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
@Component
public class CartDao {

    /**
     * Makes a GET request using the Commercetools API.
     * @param cartId - cart ID of the cart we want information out of.
     * @return either a Mono containing the cart information or an error.
     */

    public Mono<Cart> findCartById(String cartId) {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();

        return Mono.just(apiRoot.carts().withId(cartId).get().executeBlocking().getBody());
    }

    /**
     * Makes a POST request to create an empty cart.
     * @param cartDetails - cart details containing a currency code and country code.
     * @return an empty cart with the specified currency code and country code.
     */

    public Cart createEmptyCart(CartDetails cartDetails) {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails, false);

        return createCartObject(apiRoot, newCartDraft);
     }

    /**
     * Makes a POST request to create an empty cart.
     * @param lineItemArrayList - arraylist containing the list of line items to include in the cart.
     * @param cartDetails - cart details containing a currency code, country code.
     * @return a cart with the specified currency code, country code, and line items from the array.
     */

    public Cart createCartWithLineItems(CartDetails cartDetails) throws RuntimeException {
        ProjectApiRoot apiRoot = CartHelperMethods.createApiClient();
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails, true);

        return createCartObject(apiRoot, newCartDraft);
    }

    /**
     * Makes a POST request to create an empty cart out of a given cart draft.
     * @param cartDraft - cart draft that we want to create a cart out of.
     * @return a cart object made from the cart draft's information.
     */

    public static Cart createCartObject(ProjectApiRoot apiRoot, CartDraft cartDraft) {
        return apiRoot.carts().post(cartDraft).executeBlocking().getBody();
    }
}
