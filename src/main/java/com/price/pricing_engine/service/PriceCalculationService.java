package com.price.pricing_engine.service;

import com.price.pricing_engine.dto.PricingContext;

public interface PriceCalculationService {
    void processPricingRequest(PricingContext pricingContext);
}

