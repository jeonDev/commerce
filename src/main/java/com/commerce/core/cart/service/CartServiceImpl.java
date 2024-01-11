package com.commerce.core.cart.service;

import com.commerce.core.cart.entity.Cart;
import com.commerce.core.common.exception.CommerceException;
import com.commerce.core.common.exception.ExceptionStatus;
import com.commerce.core.member.entity.Member;
import com.commerce.core.product.entity.Product;
import com.commerce.core.cart.repository.CartRepository;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.cart.vo.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final MemberService memberService;
    private final ProductService productService;

    @Override
    public Cart add(CartDto dto) {
        Long memberSeq = dto.getMemberSeq();
        Long productSeq = dto.getProductSeq();
        Member member = memberService.selectMember(memberSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));
        Product product = productService.selectProduct(productSeq)
                .orElseThrow(() -> new CommerceException(ExceptionStatus.ENTITY_IS_EMPTY));

        Cart cart = Cart.builder()
                .product(product)
                .member(member)
                .cartCount(dto.getProductCount())
                .build();

        return cartRepository.save(cart);
    }

    @Override
    public int drop(Cart cart) {
        cartRepository.delete(cart);
        return 1;
    }

    @Override
    public List<Cart> selectCart(Member member) {
        return cartRepository.findByMember(member);
    }
}
