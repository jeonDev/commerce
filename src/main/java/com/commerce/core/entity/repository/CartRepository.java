package com.commerce.core.entity.repository;

import com.commerce.core.entity.Cart;
import com.commerce.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember(Member member);
}
