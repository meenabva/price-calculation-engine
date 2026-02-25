package com.price.pricing_engine.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceCalculationStrategyFactory {

    private static final Logger logger = LoggerFactory.getLogger(PriceCalculationStrategyFactory.class);

    BasePriceCalculationStrategy basePriceCalculationStrategy;

    MultiDriverPriceCalculationStrategy multiDriverPriceCalculationStrategy;

    @Autowired
    private PriceCalculationStrategyFactory(BasePriceCalculationStrategy basePriceCalculationStrategy,
                                            MultiDriverPriceCalculationStrategy multiDriverPriceCalculationStrategy) {
        this.basePriceCalculationStrategy = basePriceCalculationStrategy;
        this.multiDriverPriceCalculationStrategy = multiDriverPriceCalculationStrategy;
    }

    public PriceCalculationStrategy getPriceCalculationStrategy(String strategy) {
        logger.debug("Getting price calculation strategy for strategy: {}", strategy);
        if (strategy == null || strategy.isEmpty()) {
            throw new IllegalArgumentException("Strategy cannot be null or empty");
        }
        if("BASE".equalsIgnoreCase(strategy)) {
            return basePriceCalculationStrategy;
        }
        if("MULTI_DRIVER".equalsIgnoreCase(strategy)) {
            return multiDriverPriceCalculationStrategy;
        }
        throw new IllegalArgumentException("Strategy is null or invalid");
    }
}
