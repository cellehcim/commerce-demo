package com.cellehcim.commercedemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.api.models.cart.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



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
    public Cart createCartWithoutCurrencyCode() {
        return cartService.createCart();
    }
    
    @PostMapping(params={"currency"})
    public Cart createCartWithCurrencyCodeRequestParam(@RequestParam(name="currency") String currency) {    
        return cartService.createCart(currency);
    }
}