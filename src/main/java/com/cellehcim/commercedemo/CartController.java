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

    @GetMapping("/{cartId}")
    public Cart findCartById(@PathVariable String cartId) {
        return cartService.findCartById(cartId);
    }

    @PostMapping()
    public Cart createCart(@RequestBody CartDetails cartDetails) {
        
        String[] lineItemSkus = cartDetails.getLineItemSkus();
        System.out.println("PASS");
        
        if (lineItemSkus != null) {
            return cartService.createCartWithLineItems(lineItemSkus);
        } else {
            return cartService.createEmptyCart();
        }
    }
}