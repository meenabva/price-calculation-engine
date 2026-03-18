package com.price.pricing_engine.resource;

import com.price.pricing_engine.dto.AIRecomendationDTO;
import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.service.AIAdvisorService;
import com.price.pricing_engine.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductResource {

    private final ProductService productService;

    private final AIAdvisorService aiAdvisorService;

    public ProductResource(ProductService productService,  AIAdvisorService aiAdvisorService) {
        this.productService = productService;
        this.aiAdvisorService = aiAdvisorService;
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

    @GetMapping("/ai")
    public AIRecomendationDTO getAIAdvice(@RequestParam String productId) {
        return aiAdvisorService.getPricingAdvice(productId);
    }
}
