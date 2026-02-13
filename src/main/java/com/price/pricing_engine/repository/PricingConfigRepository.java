package com.price.pricing_engine.repository;

import com.price.pricing_engine.entity.PricingConfig;
import com.price.pricing_engine.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricingConfigRepository extends JpaRepository<PricingConfig, Long> {

    List<PricingConfig> findByProductType(String productType);
}
