package com.price.pricing_engine.service.impl;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.service.PriceService;
import com.price.pricing_engine.strategy.PriceCalculationStrategy;
import com.price.pricing_engine.strategy.PriceCalculationStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

public class PriceServiceImpl implements PriceService {

    private final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    @Override
    public BigDecimal calculateProductPrice(PricingContext pricingContext) {
        logger.debug("Calculating price for pricingContext={}", pricingContext);
        try {
            Map<String, Object> metadata = pricingContext.metadata();
            String strategy = (String) metadata.get("strategy");
            PriceCalculationStrategy priceCalculationStrategy = PriceCalculationStrategyFactory
                    .getInstance().getPriceCalculationStrategy(strategy);
            return priceCalculationStrategy.calculateProductPrice(pricingContext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
