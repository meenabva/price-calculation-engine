package com.price.pricing_engine.service;

import com.price.pricing_engine.dto.PricingContext;

import java.math.BigDecimal;

public interface ProductService {

    public BigDecimal calculateProductPrice(PricingContext pricingContext);

    void calculateProductPriceAsync(PricingContext pricingContext);

}
