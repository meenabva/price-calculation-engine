package com.price.pricing_engine.kafka;

import com.price.pricing_engine.dto.PricingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PriceRequestProducer {
    private static final Logger logger = LoggerFactory.getLogger(PriceRequestProducer.class);

    @Value("${pricing.kafka.topic:price-requests}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PriceRequestProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPriceRequest(PricingContext pricingContext) {
        logger.info("Publishing pricing request to Kafka: {}", pricingContext);
        // It will still work perfectly with your record
        kafkaTemplate.send(topic, pricingContext);
    }
}

