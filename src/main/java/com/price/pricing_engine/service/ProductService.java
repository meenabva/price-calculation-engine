package com.price.pricing_engine.service;

import com.price.pricing_engine.dto.PricingContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ProductService {

    public BigDecimal calculateProductPrice(PricingContext pricingContext);

    void calculateProductPriceAsync(PricingContext pricingContext);

}
