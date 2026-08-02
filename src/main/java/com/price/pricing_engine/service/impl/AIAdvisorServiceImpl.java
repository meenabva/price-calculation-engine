package com.price.pricing_engine.service.impl;

import com.price.pricing_engine.dto.AIRecomendationDTO;
import com.price.pricing_engine.entity.Product;
import com.price.pricing_engine.repository.ProductRepository;
import com.price.pricing_engine.service.AIAdvisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIAdvisorServiceImpl implements AIAdvisorService {

    private static final Logger log = LoggerFactory.getLogger(AIAdvisorServiceImpl.class);

    private final ChatClient chatClient;

    private ProductRepository productRepository;

    public AIAdvisorServiceImpl(ChatClient.Builder builder,
                                ProductRepository productRepository) {
        this.chatClient = builder.build();
        this.productRepository = productRepository;
    }

    @Override
    public AIRecomendationDTO getPricingAdvice(String productId) {
        log.debug("Getting AI pricing advice for productId: {}", productId);
        Product product = productRepository.findById(Long.valueOf(productId))
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id: " + productId));
        String prompt = """
                You are a pricing strategy expert.
                
                Given the following product type:
                %s
                
                Suggest pricing drivers and their weightage (in percentage).
                
                Return STRICT JSON in this format:
                {
                  "recommendations": [
                    {
                      "driver": "string",
                      "weightage": number
                    }
                  ]
                }
                """.formatted(product.getType());
        return chatClient.prompt(prompt)
                .call()
                .entity(AIRecomendationDTO.class);
    }
}
