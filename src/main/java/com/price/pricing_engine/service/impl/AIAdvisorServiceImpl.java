package com.price.pricing_engine.service.impl;

import com.price.pricing_engine.repository.ProductRepository;
import com.price.pricing_engine.service.AIAdvisorService;
import org.springframework.ai.chat.client.ChatClient;

public class AIAdvisorServiceImpl implements AIAdvisorService {

    private final ChatClient chatClient;

    private ProductRepository productRepository;

    public AIAdvisorServiceImpl(ChatClient.Builder builder,
                                ProductRepository productRepository) {
        this.chatClient = builder.build();
        this.productRepository = productRepository;
    }

    @Override
    public String getPricingAdvice(String productId) {
        productRepository.findById(Long.valueOf(productId))
                .orElseThrow(() -> new IllegalArgumentException("Product not found for id: " + productId));
        String prompt = """
        You are a pricing strategy expert.

        Given the following product data:
        %s

        Suggest pricing drivers and corresponding weightage % to apply.
        Return only JSON:
        { "recommendations": [
        {
            "driver": "...",
            "weightage": "...",
        }
        ] }
        """.formatted(context.toString());
        return chatClient.prompt(prompt)
                .call()
                .entity(AIRecomendationDTO.class);
    }
}
