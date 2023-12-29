package com.commerce.core.service.cart;

import com.commerce.core.entity.Cart;
import com.commerce.core.entity.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void add() {

    }

    @Override
    public void drop() {

    }

    @Override
    public Cart selectCart(Long memberSeq) {
        return null;
    }
}
