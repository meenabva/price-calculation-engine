package com.price.pricing_engine.driver;

import com.price.pricing_engine.dto.PricingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class CommisionDriverImpactCalculator implements DriverImpactCalculator{

    private final Logger log = LoggerFactory.getLogger(CommisionDriverImpactCalculator.class);

    @Override
    public BigDecimal calculateDriverPrice(PricingContext context, BigDecimal weightage) {
        log.debug("Calculating driver price for pricingContext: {}", context);
        try {
            return new BigDecimal("10.00");
        } catch (Exception e) {
            log.error("Error calculating driver price for pricingContext: {}", context, e);
            throw new RuntimeException(e);
        }
    }
}
