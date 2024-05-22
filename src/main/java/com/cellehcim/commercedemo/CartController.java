package com.cellehcim.commercedemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.models.cart.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    
    /**
     * Returns the cart information for a given Commercetools cart ID.
     * @param cartId - ID of the cart that we want information from.
     * @return - cart if it exists, error otherwise.
     */

    @GetMapping("/{cartId}")
    public Cart findCartById(@PathVariable String cartId) {
        return cartService.findCartById(cartId);
    }

    /**
     * Creates a cart object given a country code, currency code, and product SKUs.
     * @param cartDetails - response body containing our cart creation information.
     * @return - cart information if we can successfully create the corresponding cart, error if otherwise.
     */

    @PostMapping()
    public Cart createCart(@RequestBody CartDetails cartDetails) {
        return cartService.createCart(cartDetails);
    }
}