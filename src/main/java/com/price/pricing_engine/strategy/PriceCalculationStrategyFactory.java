package com.price.pricing_engine.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceCalculationStrategyFactory {

    private static final Logger logger = LoggerFactory.getLogger(PriceCalculationStrategyFactory.class);

    private static final PriceCalculationStrategyFactory instance = new PriceCalculationStrategyFactory();

    private PriceCalculationStrategyFactory() {}

    public static PriceCalculationStrategyFactory getInstance() {
        return instance;
    }

    public PriceCalculationStrategy getPriceCalculationStrategy(String strategy) {
        logger.debug("Getting price calculation strategy for strategy: {}", strategy);
        if (strategy == null || strategy.isEmpty()) {
            throw new IllegalArgumentException("Strategy cannot be null or empty");
        }
        if("BASE".equalsIgnoreCase(strategy)) {
            return new BasePriceCalculationStrategy();
        }
        if("MULTI_DRIVER".equalsIgnoreCase(strategy)) {
            return new MultiDriverPriceCalculationStrategy();
        }
        throw new IllegalArgumentException("Strategy is null or invalid");
    }
}
