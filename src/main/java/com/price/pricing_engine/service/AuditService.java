package com.price.pricing_engine.service;

import java.math.BigDecimal;
import com.price.pricing_engine.dto.PricingContext;

public interface AuditService {
    void saveAudit(PricingContext pricingContext, BigDecimal calculatedPrice);
}

