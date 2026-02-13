package com.price.pricing_engine.repository;

import com.price.pricing_engine.entity.PricingConfig;
import com.price.pricing_engine.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingConfigRepository extends JpaRepository<PricingConfig, Long> {

}
