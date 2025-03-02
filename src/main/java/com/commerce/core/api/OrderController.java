package com.commerce.core.api;

import com.commerce.core.api.request.OrderRequest;
import com.commerce.core.api.request.PaymentRequest;
import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.order.service.OrderService;
import com.commerce.core.order.service.OrderViewService;
import com.commerce.core.order.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문/결제 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderViewService orderViewService;
    private final PaymentService paymentService;

    @PostMapping("/v1/order")
    @Operation(summary = "상품 주문", description = "고객이 상품을 주문한다.")
    public ResponseVO<Long> order(@RequestBody OrderRequest request) {
        return ResponseVO.<Long>builder()
                .data(orderService.order(request.toRequest())
                        .getOrderSeq())
                .build();
    }

    @PostMapping("/v1/payment")
    @Operation(summary = "주문 결제", description = "상품 주문 내역에 대한 결제 처리를 진행한다.")
    public ResponseVO<Object> payment(@RequestBody PaymentRequest request) {
        paymentService.payment(request.toRequest());
        return ResponseVO.builder()
                .build();
    }

    @GetMapping("/admin/order/view")
    @Operation(summary = "주문정보 조회", description = "관리자가 주문 정보를 조회한다.")
    public ResponseVO<PageListVO<?>> selectOrderView(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) String pageNumber,
                                                     @RequestParam(name = "pageSize", defaultValue = "10", required = false) String pageSize) {
        return ResponseVO.<PageListVO<?>>builder()
                .data(orderViewService.selectOrderView(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)))
                .build();
    }

}
