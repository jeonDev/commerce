package com.commerce.core.cart.repository;

import com.commerce.core.cart.entity.Cart;
import com.commerce.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember(Member member);
}
