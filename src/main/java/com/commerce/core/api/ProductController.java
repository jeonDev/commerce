package com.commerce.core.api;

import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.service.ProductViewService;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductViewResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductViewService productViewService;

    @GetMapping("/view")
    @Operation(summary = "상품 목록", description = "고객에게 보여줄 상품 목록을 출력한다. (MongoDB)")
    public ResponseVO<List<ProductViewResDto>> productViewList() {
        return ResponseVO.<List<ProductViewResDto>>builder()
                .data(productViewService.selectProductViewList())
                .build();
    }

    @PostMapping("/register")
    @Operation(summary = "상품 등록", description = "관리자가 상품을 등록한다.")
    public ResponseVO<Object> productRegister(@RequestBody ProductDto dto) {
        productService.add(dto);
        return ResponseVO.builder()
                .build();
    }
}
