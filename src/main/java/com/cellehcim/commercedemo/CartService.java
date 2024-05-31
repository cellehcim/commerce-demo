package com.cellehcim.commercedemo;

import com.commercetools.api.models.cart.Cart;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartDao cartDao;

    /**
     * Returns cart information for a given Commercetools cart ID.
     * @param cartId - ID of the cart that we want information from.
     * @return A Mono emitting the corresponding cart (and its infirmation) if it exists, error otherwise.
     */
    
    public Mono<Cart> findCartById(String cartId) {
        return cartDao.findCartById(cartId);
    }

    /**
     * Creates a cart given its country code, currency code, and if applicable, list of line item details.
     * @param cartDetails - information for the cart that we want to create.
     * @return A Mono emitting a cart object made from its request information.
     */

    public Mono<Cart> createCart(CartDetails cartDetails) {

        if (cartDetails.getLineItems() != null) {

            if (cartDetails.getLineItems().size() > 0) {
                return cartDao.createCartWithLineItems(cartDetails);
            } else {
                return cartDao.createEmptyCart(cartDetails);
            }
        } else {
            return cartDao.createEmptyCart(cartDetails);
        }
    }
    /**
     * Deletes a cart object given its cart ID
     * @param cartID ID of the cart to delete
     * @return a Mono of the now-deleted cart, error otherwise.
     */
    public Mono<Cart> deleteCart(String cartId) {
        return cartDao.deleteCart(cartId);
    }
}
