package com.price.pricing_engine.resource;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/calculate-price")
    public void calculatePriceAsync(@RequestBody PricingContext pricingContext) {
        productService.calculateProductPriceAsync(pricingContext);
    }

    @GetMapping("/{productId}/price-history")
    public List<BigDecimal> getPriceHistory(Long productId) {
        // Placeholder for price history retrieval logic
        return null;
    }
}
