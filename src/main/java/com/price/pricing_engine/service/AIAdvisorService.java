package com.price.pricing_engine.service;

import com.price.pricing_engine.dto.AIRecomendationDTO;

public interface AIAdvisorService {

    public AIRecomendationDTO getPricingAdvice(String productId);
}
