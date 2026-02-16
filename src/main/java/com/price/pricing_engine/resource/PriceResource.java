package com.price.pricing_engine.resource;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.service.PriceService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceResource {

    private PriceService priceService;

    public PriceResource(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/calculate-price")
    public BigDecimal calculatePrice(@RequestBody PricingContext pricingContext) {
        return priceService.calculateProductPrice(pricingContext);
    }

    @GetMapping("/{productId}/price-history")
    public List<BigDecimal> getPriceHistory(Long productId) {
        // Placeholder for price history retrieval logic
        return null;
    }
}
