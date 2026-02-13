package com.price.pricing_engine.repository;

import com.price.pricing_engine.entity.PricingDriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingDriverRepository extends JpaRepository<PricingDriverEntity, Long> {
}
