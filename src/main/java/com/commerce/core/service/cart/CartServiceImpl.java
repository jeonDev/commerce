package com.commerce.core.service.cart;

import com.commerce.core.entity.Cart;
import com.commerce.core.entity.Member;
import com.commerce.core.entity.Product;
import com.commerce.core.entity.repository.CartRepository;
import com.commerce.core.service.member.MemberService;
import com.commerce.core.service.product.ProductService;
import com.commerce.core.vo.cart.CartDto;
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
        Member member = memberService.selectMember(memberSeq);
        Product product = productService.selectProduct(productSeq);

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
