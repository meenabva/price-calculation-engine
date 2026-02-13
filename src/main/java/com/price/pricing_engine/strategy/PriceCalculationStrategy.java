package com.price.pricing_engine.strategy;

import com.price.pricing_engine.dto.PricingContext;

import java.math.BigDecimal;

public interface PriceCalculationStrategy {

    public BigDecimal calculateProductPrice(PricingContext pricingContext);

}
