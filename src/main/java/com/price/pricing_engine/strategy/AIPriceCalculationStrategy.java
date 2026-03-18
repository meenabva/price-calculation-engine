package com.price.pricing_engine.strategy;

import com.price.pricing_engine.driver.DriverImpactCalculator;
import com.price.pricing_engine.driver.DriverImpactCalculatorFactory;
import com.price.pricing_engine.dto.AIRecomendationDTO;
import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.entity.PricingConfig;
import com.price.pricing_engine.entity.Product;
import com.price.pricing_engine.entity.ProductPricingAudit;
import com.price.pricing_engine.repository.PricingConfigRepository;
import com.price.pricing_engine.repository.ProductPricingAuditRepository;
import com.price.pricing_engine.repository.ProductRepository;
import com.price.pricing_engine.service.AIAdvisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

public class AIPriceCalculationStrategy implements PriceCalculationStrategy {

    private final AIAdvisorService aiAdvisorService;

    private final Logger logger = LoggerFactory.getLogger(AIPriceCalculationStrategy.class);

    private ProductRepository productRepository;

    private ProductPricingAuditRepository productPricingAuditRepository;

    public AIPriceCalculationStrategy(AIAdvisorService aiAdvisorService,
                                      ProductRepository productRepository,
                                      ProductPricingAuditRepository productPricingAuditRepository) {
        this.aiAdvisorService = aiAdvisorService;
        this.productRepository = productRepository;
        this.productPricingAuditRepository = productPricingAuditRepository;
    }

    @Override
    public BigDecimal calculateProductPrice(PricingContext pricingContext) {
        logger.debug("Calculating price for pricing context {} with ai driver strategy.", pricingContext);
        try {
            Long productId = pricingContext.productId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " not found"));
            AIRecomendationDTO aiRecomendationDTO = aiAdvisorService.getPricingAdvice(String.valueOf(productId));
            if(aiRecomendationDTO == null || CollectionUtils.isEmpty(aiRecomendationDTO.recommendations())) {
                throw new NoSuchElementException("No ai recomendations found for product type " + product.getType());
            }
            List<CompletableFuture<BigDecimal>> futures = aiRecomendationDTO.recommendations().stream().map(driverWeightageDTO -> {
                String driverName = driverWeightageDTO.driverName();
                DriverImpactCalculator driverImpactCalculator = DriverImpactCalculatorFactory
                        .getInstance().getDriverImpactCalculator(driverName);
                return CompletableFuture.supplyAsync(() -> driverImpactCalculator
                        .calculateDriverPrice(pricingContext, driverWeightageDTO.weightage()));
            }).toList();
            BigDecimal price = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> futures.stream()
                            .map(CompletableFuture::join)
                            .reduce(product.getBasePrice(), BigDecimal::add))
                    .join();
            ProductPricingAudit productPricingAudit = new ProductPricingAudit();
            productPricingAudit.setCalculatedPrice(price);
            productPricingAudit.setProduct(product);
            productPricingAuditRepository.save(productPricingAudit);
            return price;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
