package com.commerce.core.api;

import com.commerce.core.product.service.ProductService;
import com.commerce.core.product.service.ProductViewService;
import com.commerce.core.product.vo.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductViewService productViewService;

    @GetMapping("/view")
    public void pointCharge() {
        productViewService.selectProductViewList();
    }

    @PostMapping("/regist")
    public void pointCharge(@RequestBody ProductDto dto) {
        productService.add(dto);
    }
}
