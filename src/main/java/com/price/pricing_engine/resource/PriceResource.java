package com.price.pricing_engine.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceResource {

    private PriceResource() {}

    @PostMapping("/calculate-price")
    public BigDecimal calculatePrice(Long productId) {
        // Placeholder for price calculation logic
        return BigDecimal.ZERO;
    }

    @GetMapping("/{productId}/price-history")
    public List<BigDecimal> getPriceHistory(Long productId) {
        // Placeholder for price history retrieval logic
        return null;
    }
}
