package com.price.pricing_engine.service.impl;

import com.price.pricing_engine.dto.PricingContext;
import com.price.pricing_engine.entity.Product;
import com.price.pricing_engine.entity.ProductPricingAudit;
import com.price.pricing_engine.repository.ProductPricingAuditRepository;
import com.price.pricing_engine.repository.ProductRepository;
import com.price.pricing_engine.service.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AuditServiceImpl implements AuditService {
    private final ProductPricingAuditRepository auditRepository;
    private final ProductRepository productRepository;

    public AuditServiceImpl(ProductPricingAuditRepository auditRepository, ProductRepository productRepository) {
        this.auditRepository = auditRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void saveAudit(PricingContext pricingContext, BigDecimal calculatedPrice) {
        Long productId = pricingContext.productId();
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found for id: " + productId);
        }
        ProductPricingAudit audit = new ProductPricingAudit();
        audit.setProduct(productOpt.get());
        audit.setCalculatedPrice(calculatedPrice);
        auditRepository.save(audit);
    }
}

