package com.price.pricing_engine.strategy;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.entity.Product;
import com.price.pricing_engine.entity.ProductPricingAudit;
import com.price.pricing_engine.repository.ProductPricingAuditRepository;
import com.price.pricing_engine.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

public class BasePriceCalculationStrategy implements PriceCalculationStrategy {

    private final Logger logger = LoggerFactory.getLogger(BasePriceCalculationStrategy.class);

    private ProductRepository productRepository;

    private ProductPricingAuditRepository productPricingAuditRepository;

    BasePriceCalculationStrategy() {}

    @Autowired
    BasePriceCalculationStrategy(ProductRepository productRepository,
                                 ProductPricingAuditRepository productPricingAuditRepository) {
        this.productRepository = productRepository;
        this.productPricingAuditRepository = productPricingAuditRepository;
    }

    @Override
    public BigDecimal calculateProductPrice(PricingContext pricingContext) {
        logger.debug("Calculating product price for pricing context {} with base strategy.", pricingContext);
        try {
            Long productId = pricingContext.productId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException(
                            "Product with id " + productId + " not found"));
            BigDecimal price = product.getBasePrice();
            ProductPricingAudit productPricingAudit = new ProductPricingAudit();
            productPricingAudit.setCalculatedPrice(price);
            productPricingAudit.setProduct(product);
            productPricingAuditRepository.save(productPricingAudit);
            return price;
        } catch (Exception e) {
            logger.error("Error calculating product price for pricing context {} with base strategy.", pricingContext, e);
            throw new RuntimeException(e);
        }
    }
}
