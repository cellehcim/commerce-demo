package com.cellehcim.commercedemo;

import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.cart.LineItemDraft;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartDao cartDao;

    public Cart findCartById(String cartId) {
        return cartDao.findCartById(cartId);
    }

    public Cart createCart(CartDetails cartDetails) {

        if (cartDetails.getLineItemSkus() != null) {
            List<LineItemDraft> lineItemArrayList = CartHelperMethods.createLineItems(cartDetails.getLineItemSkus());

            if (lineItemArrayList.size() > 0) {
                return cartDao.createCartWithLineItems(lineItemArrayList, cartDetails);
            } else {
                return cartDao.createEmptyCart(cartDetails);
            }
        } else {
            return cartDao.createEmptyCart(cartDetails);
        }
    }
}
