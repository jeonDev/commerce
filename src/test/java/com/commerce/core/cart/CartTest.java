package com.commerce.core.cart;

import com.commerce.core.cart.entity.Cart;
import com.commerce.core.member.entity.Member;
import com.commerce.core.cart.service.CartService;
import com.commerce.core.cart.vo.CartDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;

    @Test
    void cartAdd() {
        CartDto dto = new CartDto();
        dto.setMemberSeq(1L);
        dto.setProductSeq(1L);
        dto.setProductCount(3L);
        Cart cart = cartService.add(dto);

        assertThat(cart.getMember().getMemberSeq()).isEqualTo(dto.getMemberSeq());
        assertThat(cart.getProduct().getProductSeq()).isEqualTo(dto.getProductSeq());
        assertThat(cart.getCartCount()).isEqualTo(dto.getProductCount());
    }

    @Test
    void cardDelete() {
        Member member = Member.builder()
                .memberSeq(1L)
                .build();
        cartService.selectCart(member).stream()
                .forEach(item -> {
                    int result = cartService.drop(item);
                    assertThat(result).isEqualTo(1);
                });
    }
}
