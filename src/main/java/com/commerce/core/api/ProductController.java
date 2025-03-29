package com.commerce.core.api;

import com.commerce.core.api.request.ProductRegisterRequest;
import com.commerce.core.api.request.ProductStockRequest;
import com.commerce.core.api.response.ProductInfoResponse;
import com.commerce.core.api.response.ProductOrderResponse;
import com.commerce.core.common.vo.PageListVO;
import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.service.ProductStockService;
import com.commerce.core.product.service.ProductViewService;
import com.commerce.core.product.domain.dto.AdminProductListDto;
import com.commerce.core.product.service.response.AdminProductListServiceResponse;
import com.commerce.core.product.service.response.ProductViewServiceResponse;
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

    private final ProductService productService;
    private final ProductStockService productStockService;
    private final ProductViewService productViewService;

    @GetMapping("/v2/product/view")
    @Operation(summary = "상품 목록", description = "고객에게 보여줄 상품 목록을 출력한다. (MongoDB)")
    public ResponseVO<PageListVO<ProductViewServiceResponse>> productViewList(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) String pageNumber,
                                                                              @RequestParam(name = "pageSize", defaultValue = "10", required = false) String pageSize) {
        return ResponseVO.<PageListVO<ProductViewServiceResponse>>builder()
                .data(productViewService.selectProductViewList(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)))
                .build();
    }

    @GetMapping("/v2/productInfo/{productInfoSeq}")
    @Operation(summary = "상품 상세", description = "상품 상세내용을 출력한다.")
    public ResponseVO<ProductInfoResponse> productDetailList(@PathVariable("productInfoSeq") String productInfoSeq) {
        return ResponseVO.<ProductInfoResponse>builder()
                .data(ProductInfoResponse.from(
                        productViewService.selectProductViewDetail(Long.valueOf(productInfoSeq))
                ))
                .build();
    }

    @GetMapping("/v2/product/{productSeq}")
    @Operation(summary = "상품 상세", description = "상품 상세내용을 출력한다.")
    public ResponseVO<ProductOrderResponse> productList(@PathVariable("productSeq") String productSeq) {
        return ResponseVO.<ProductOrderResponse>builder()
                .data(ProductOrderResponse.from(
                        productViewService.selectProductView(Long.valueOf(productSeq))
                ))
                .build();
    }

    @PostMapping("/admin/product/register")
    @Operation(summary = "상품 등록", description = "관리자가 상품을 등록한다.")
    public ResponseVO<Object> productRegister(@Valid @RequestBody ProductRegisterRequest request) {
        productService.add(request.toRequest());
        return ResponseVO.builder()
                .build();
    }

    @PostMapping("/admin/stock/adjustment")
    @Operation(summary = "상품 재고 조정", description = "관리자가 상품의 재고를 조정한다.")
    public ResponseVO<Object> productStockAdjustment(@RequestBody ProductStockRequest request) {
        productStockService.productStockAdjustment(request.toRequest());
        return ResponseVO.builder()
                .build();
    }

    @GetMapping("/admin/product/list")
    public ResponseVO<PageListVO<AdminProductListServiceResponse>> adminProductList(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) String pageNumber,
                                                                                    @RequestParam(name = "pageSize", defaultValue = "10", required = false) String pageSize) {
        return ResponseVO.<PageListVO<AdminProductListServiceResponse>>builder()
                .data(productViewService.selectProductList(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)))
                .build();
    }
}
