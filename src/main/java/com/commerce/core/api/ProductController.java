package com.commerce.core.api;

import com.commerce.core.api.request.ProductRegisterRequest;
import com.commerce.core.api.request.ProductStockRequest;
import com.commerce.core.common.type.HttpResponse;
import com.commerce.core.product.facade.ProductFacade;
import com.commerce.core.product.facade.ProductQueryFacade;
import com.commerce.core.product.facade.ProductStockFacade;
import com.commerce.core.product.facade.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductFacade productFacade;
    private final ProductStockFacade productStockFacade;
    private final ProductQueryFacade productQueryFacade;

    @GetMapping("/v2/product/{productSeq}")
    @Operation(summary = "상품 상세", description = "상품 상세내용을 출력한다.")
    public HttpResponse<ProductResponse> productList(@PathVariable("productSeq") String productSeq) {
        return HttpResponse.<ProductResponse>builder()
                .data(productQueryFacade.getProduct(Long.valueOf(productSeq)))
                .build();
    }

    @PostMapping("/admin/product/register")
    @Operation(summary = "상품 등록", description = "관리자가 상품을 등록한다.")
    public HttpResponse<Object> productRegister(@Valid @RequestBody ProductRegisterRequest request) {
        productFacade.add(request.toRequest());
        return HttpResponse.builder()
                .build();
    }

    @PostMapping("/admin/stock/adjustment")
    @Operation(summary = "상품 재고 조정", description = "관리자가 상품의 재고를 조정한다.")
    public HttpResponse<Object> productStockAdjustment(@RequestBody ProductStockRequest request) {
        productStockFacade.adjustment(request.productSeq(), request.productStockProcessStatus(), request.stock());
        return HttpResponse.builder()
                .build();
    }

}
