package com.commerce.core.service.cart;

import com.commerce.core.entity.Cart;

public interface CartService {

    void add();
    void drop();
    Cart selectCart(Long memberSeq);

}
