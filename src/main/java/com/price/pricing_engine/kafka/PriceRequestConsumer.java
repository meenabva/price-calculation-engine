package com.price.pricing_engine.kafka;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.service.PriceCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PriceRequestConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PriceRequestConsumer.class);

    private final PriceCalculationService priceCalculationService;

    @Autowired
    public PriceRequestConsumer(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    @KafkaListener(topics = "${pricing.kafka.topic:price-requests}", groupId = "pricing-group")
    public void consume(PricingContext pricingContext) {
        logger.info("Received pricing request from Kafka: {}", pricingContext);
        priceCalculationService.processPricingRequest(pricingContext);
    }
}
