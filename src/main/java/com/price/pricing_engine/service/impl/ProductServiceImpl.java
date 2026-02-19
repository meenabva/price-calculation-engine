package com.price.pricing_engine.service.impl;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.kafka.PriceRequestProducer;
import com.price.pricing_engine.service.ProductService;
import com.price.pricing_engine.strategy.PriceCalculationStrategy;
import com.price.pricing_engine.strategy.PriceCalculationStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;

public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final PriceRequestProducer priceRequestProducer;

    @Autowired
    public ProductServiceImpl(PriceRequestProducer priceRequestProducer) {
        this.priceRequestProducer = priceRequestProducer;
    }

    @Override
    public BigDecimal calculateProductPrice(PricingContext pricingContext) {
        logger.debug("Calculating price for pricingContext={}", pricingContext);
        try {
            Map<String, Object> metadata = pricingContext.metadata();
            String strategy = (String) metadata.get("strategy");
            PriceCalculationStrategy priceCalculationStrategy = PriceCalculationStrategyFactory
                    .getInstance().getPriceCalculationStrategy(strategy);
            return priceCalculationStrategy.calculateProductPrice(pricingContext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void calculateProductPriceAsync(PricingContext pricingContext) {
        priceRequestProducer.sendPriceRequest(pricingContext);
    }
}
