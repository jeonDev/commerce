package com.commerce.core.api;

import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.order.service.PaymentService;
import com.commerce.core.order.vo.OrderDto;
import com.commerce.core.order.vo.PaymentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문/결제 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/order")
@RestController
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/order")
    @Operation(summary = "상품 주문", description = "고객이 상품을 주문한다.")
    public ResponseVO<Object> order(@RequestBody OrderDto dto) {
        orderService.order(dto);
        return ResponseVO.builder()
                .build();
    }

    @PostMapping("/payment")
    @Operation(summary = "주문 결제", description = "상품 주문 내역에 대한 결제 처리를 진행한다.")
    public ResponseVO<Object> payment(@RequestBody PaymentDto dto) {
        paymentService.payment(dto);
        return ResponseVO.builder()
                .build();
    }

}
