package com.price.pricing_engine.dto;

import java.util.Map;

public record PricingContext(Long productId, Map<String, Object> metadata) {

    public PricingContext(){
        this(null, null);
    }
}
