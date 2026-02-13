package com.price.pricing_engine.strategy;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.entity.Product;
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

    BasePriceCalculationStrategy() {}

    @Autowired
    BasePriceCalculationStrategy(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal calculateProductPrice(PricingContext pricingContext) {
        logger.debug("Calculating product price for pricing context {} with base strategy.", pricingContext);
        try {
            Long productId = pricingContext.productId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException(
                            "Product with id " + productId + " not found"));
            return product.getBasePrice();
        } catch (Exception e) {
            logger.error("Error calculating product price for pricing context {} with base strategy.", pricingContext, e);
            throw new RuntimeException(e);
        }
    }
}
