package com.commerce.core.service.cart;

import com.commerce.core.entity.Cart;
import com.commerce.core.entity.Member;
import com.commerce.core.vo.cart.CartDto;

import java.util.List;

public interface CartService {

    Cart add(CartDto dto);
    int drop(Cart cart);
    List<Cart> selectCart(Member member);

}
