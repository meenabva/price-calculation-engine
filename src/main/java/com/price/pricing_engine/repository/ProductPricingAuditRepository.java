package com.price.pricing_engine.repository;

import com.price.pricing_engine.entity.ProductPricingAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPricingAuditRepository extends JpaRepository<ProductPricingAudit, Long> {

}
