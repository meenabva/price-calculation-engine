package com.price.pricing_engine.service.impl;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.service.AuditService;
import com.price.pricing_engine.service.PriceCalculationService;
import com.price.pricing_engine.strategy.PriceCalculationStrategy;
import com.price.pricing_engine.strategy.PriceCalculationStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PriceCalculationServiceImpl implements PriceCalculationService {
    private static final Logger logger = LoggerFactory.getLogger(PriceCalculationServiceImpl.class);
    private final AuditService auditService;

    @Autowired
    public PriceCalculationServiceImpl(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public void processPricingRequest(PricingContext pricingContext) {
        try {
            Map<String, Object> metadata = pricingContext.metadata();
            String strategy = (String) metadata.get("strategy");
            PriceCalculationStrategy priceCalculationStrategy = PriceCalculationStrategyFactory
                    .getInstance().getPriceCalculationStrategy(strategy);
            BigDecimal price = priceCalculationStrategy.calculateProductPrice(pricingContext);
            logger.info("Calculated price: {}", price);
            auditService.saveAudit(pricingContext, price);
        } catch (Exception e) {
            logger.error("Error processing pricing request", e);
        }
    }
}

