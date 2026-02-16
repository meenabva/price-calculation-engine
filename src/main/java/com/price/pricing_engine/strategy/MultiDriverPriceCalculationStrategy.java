package com.price.pricing_engine.strategy;

import com.price.pricing_engine.driver.DriverImpactCalculator;
import com.price.pricing_engine.driver.DriverImpactCalculatorFactory;
import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.entity.PricingConfig;
import com.price.pricing_engine.entity.Product;
import com.price.pricing_engine.entity.ProductPricingAudit;
import com.price.pricing_engine.repository.PricingConfigRepository;
import com.price.pricing_engine.repository.PricingDriverRepository;
import com.price.pricing_engine.repository.ProductPricingAuditRepository;
import com.price.pricing_engine.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import java.util.concurrent.CompletableFuture;

public class MultiDriverPriceCalculationStrategy implements PriceCalculationStrategy {

    private final Logger logger = LoggerFactory.getLogger(MultiDriverPriceCalculationStrategy.class);

    private ProductRepository productRepository;

    private PricingConfigRepository pricingConfigRepository;

    private ProductPricingAuditRepository productPricingAuditRepository;

    MultiDriverPriceCalculationStrategy() {}

    @Autowired
    MultiDriverPriceCalculationStrategy(ProductRepository productRepository,
                                        PricingConfigRepository pricingConfigRepository,
                                        ProductPricingAuditRepository productPricingAuditRepository) {
        this.productRepository = productRepository;
        this.pricingConfigRepository = pricingConfigRepository;
        this.productPricingAuditRepository = productPricingAuditRepository;
    }

    @Override
    public BigDecimal calculateProductPrice(PricingContext pricingContext) {
        logger.debug("Calculating price for pricing context {} with multi driver strategy.", pricingContext);
        try {
            Long productId = pricingContext.productId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " not found"));
            List<PricingConfig> pricingConfigList = pricingConfigRepository
                                                        .findByProductType(product.getType());
            if(pricingConfigList == null || pricingConfigList.isEmpty()) {
                throw new NoSuchElementException("No pricing config found for product type " + product.getType());
            }
            List<CompletableFuture<BigDecimal>> futures = pricingConfigList.stream().map(pricingConfig -> {
                String driverName = pricingConfig.getPricingDriver().getName();
                DriverImpactCalculator driverImpactCalculator = DriverImpactCalculatorFactory
                        .getInstance().getDriverImpactCalculator(driverName);
                return CompletableFuture.supplyAsync(() -> driverImpactCalculator
                        .calculateDriverPrice(pricingContext, pricingConfig.getWeightage()));
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
