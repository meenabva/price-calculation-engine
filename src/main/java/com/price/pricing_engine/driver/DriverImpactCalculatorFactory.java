package com.price.pricing_engine.driver;

import java.util.HashMap;
import java.util.Map;

public class DriverImpactCalculatorFactory {

    private static final DriverImpactCalculatorFactory instance = new DriverImpactCalculatorFactory();

    private final Map<String, DriverImpactCalculator> calculatorMap;

    private DriverImpactCalculatorFactory() {
        calculatorMap = new HashMap<>();
        calculatorMap.put("TAX", new TaxDriverImpactCalculator());
        calculatorMap.put("LOGISTICS", new LogisticsDriverImpactCalculator());
        calculatorMap.put("DISCOUNT", new DiscountDriverImpactCalculator());
        calculatorMap.put("COMMISSION", new CommisionDriverImpactCalculator());
        calculatorMap.put("SURCHARGE", new SurchargeDriverImpactCalculator());
    }

    public static DriverImpactCalculatorFactory getInstance() {
        return instance;
    }

    public DriverImpactCalculator getDriverImpactCalculator(String driverType){
        if (driverType == null || driverType.isEmpty()) {
            throw new IllegalArgumentException("Driver type cannot be null or empty");
        }
        DriverImpactCalculator calculator = instance.calculatorMap.get(driverType.toUpperCase());
        if (calculator == null) {
            throw new IllegalArgumentException("Driver type is null or invalid");
        }
        return calculator;
    }
}
