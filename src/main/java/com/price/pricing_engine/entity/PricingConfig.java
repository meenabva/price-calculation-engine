package com.price.pricing_engine.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pricing_config")
public class PricingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricing_driver_id", referencedColumnName = "id")
    private PricingDriver pricingDriver;

    @Column(precision = 10, scale = 4)
    private BigDecimal weightage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PricingDriver getPricingDriver() {
        return pricingDriver;
    }

    public void setPricingDriver(PricingDriver pricingDriver) {
        this.pricingDriver = pricingDriver;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getWeightage() {
        return weightage;
    }

    public void setWeightage(BigDecimal weightage) {
        this.weightage = weightage;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PricingConfig{");
        sb.append("id=").append(id);
        sb.append(", productType='").append(productType).append('\'');
        sb.append(", pricingDriver=").append(pricingDriver);
        sb.append(", weightage=").append(weightage);
        sb.append('}');
        return sb.toString();
    }
}
