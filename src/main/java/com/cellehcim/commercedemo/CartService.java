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
     * @return - cart if it exists, error otherwise.
     */
    
    public Mono<Cart> findCartById(String cartId) {
        return cartDao.findCartById(cartId);
    }

    /**
     * Creates a cart given its country code, currency code, and if applicable, list of SKUs.
     * @param cartDetails - information for the cart that we want to create.
     * @return - a cart with line items if the corresponding list of SKUs was provided, an empty cart if otherwise.
     */

    public Cart createCart(CartDetails cartDetails) {

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
}
