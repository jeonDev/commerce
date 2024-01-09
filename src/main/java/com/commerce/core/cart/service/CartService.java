package com.commerce.core.cart.service;

import com.commerce.core.cart.entity.Cart;
import com.commerce.core.member.entity.Member;
import com.commerce.core.cart.vo.CartDto;

import java.util.List;

public interface CartService {

    Cart add(CartDto dto);
    int drop(Cart cart);
    List<Cart> selectCart(Member member);

}
