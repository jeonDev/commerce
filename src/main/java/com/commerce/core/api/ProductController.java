package com.commerce.core.api;

import com.commerce.core.common.vo.ResponseVO;
import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.service.ProductViewService;
import com.commerce.core.product.vo.ProductDto;
import com.commerce.core.product.vo.ProductViewResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductViewService productViewService;

    @GetMapping("/view")
    public ResponseVO<List<ProductViewResDto>> productViewList() {
        return ResponseVO.<List<ProductViewResDto>>builder()
                .data(productViewService.selectProductViewList())
                .build();
    }

    @PostMapping("/register")
    public ResponseVO<Object> productRegister(@RequestBody ProductDto dto) {
        productService.add(dto);
        return ResponseVO.builder()
                .build();
    }
}
