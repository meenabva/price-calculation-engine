package com.price.pricing_engine.driver;

import com.price.pricing_engine.dto.PricingContext;

import java.math.BigDecimal;

public interface DriverImpactCalculator {

    public BigDecimal calculateDriverPrice(PricingContext context, BigDecimal weightage);
}
