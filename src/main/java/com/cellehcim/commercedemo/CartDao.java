package com.cellehcim.commercedemo;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.CartDraft;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
@Component
public class CartDao {

    private final long CURRENT_VERSION = 1l;
    private ProjectApiRoot apiRoot;

    public CartDao() {
        apiRoot = CartHelperMethods.createApiClient();
    }

    /**
     * Makes a GET request using the Commercetools API.
     * @param cartId - cart ID of the cart we want information out of.
     * @return either a Mono containing the cart information or an error.
     */

    public Mono<Cart> findCartById(String cartId) {
        return Mono.just(apiRoot.carts().withId(cartId).get().executeBlocking().getBody());
    }

    /**
     * Makes a POST request to create an empty cart.
     * @param cartDetails - cart details containing a currency code and country code.
     * @return a Mono emitting an empty cart with the specified currency code and country code.
     */

    public Mono<Cart> createEmptyCart(CartDetails cartDetails) {
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails, false);

        return createCartObject(newCartDraft);
     }

    /**
     * Makes a POST request to create an empty cart.
     * @param lineItemArrayList - arraylist containing the list of line items to include in the cart.
     * @param cartDetails - cart details containing a currency code, country code.
     * @return a Mono emitting a cart with the specified currency code, country code, and line items from the array.
     */

    public Mono<Cart> createCartWithLineItems(CartDetails cartDetails) throws RuntimeException {
        CartDraft newCartDraft = CartHelperMethods.createCartDraftObject(apiRoot, cartDetails, true);

        return createCartObject(newCartDraft);
    }

    /**
     * Makes a request to delete a cart given its ID.
     * @param cartId the to-be-deleted cart's ID
     * @return a Mono emitting the now-deleted cart, or an error if given an ID for a non-existent cart.
     */
    
    public Mono<Cart> deleteCart(String cartId) {
        return Mono.just(apiRoot.carts().withId(cartId).delete().withVersion(CURRENT_VERSION).executeBlocking().getBody());
    }

    /**
     * Makes a POST request to create an empty cart out of a given cart draft.
     * @param cartDraft - cart draft that we want to create a cart out of.
     * @return a Mono emitting a cart object made from the cart draft's information.
     */

    public Mono<Cart> createCartObject(CartDraft cartDraft) {
        return Mono.just(apiRoot.carts().post(cartDraft).executeBlocking().getBody());
    }
}