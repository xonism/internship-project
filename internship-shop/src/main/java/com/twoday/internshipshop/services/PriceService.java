package com.twoday.internshipshop.services;

import com.twoday.internshipmodel.OrderCreateRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceService {

    @Value("${price.profit_margin}")
    private BigDecimal profitMargin;

    @Value("${price.wholesale_discount}")
    private BigDecimal wholesaleDiscount;

    @Value("${price.wholesale_amount}")
    private int wholesaleAmount;

    @PostConstruct
    private void postConstruct() {
        if (profitMargin.compareTo(wholesaleDiscount) <= 0) {
            throw new IllegalArgumentException("Profit margin has to be bigger than wholesale discount");
        }
        if (profitMargin.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new IllegalArgumentException("Profit margin has to be bigger than 0");
        }
    }

    public BigDecimal calculatePriceWithProfitMargin(BigDecimal warehousePrice) {
        return warehousePrice.add(warehousePrice.multiply(profitMargin))
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calculatePriceWithWholesaleDiscount(OrderCreateRequest orderCreateRequest) {
        BigDecimal unitPrice = orderCreateRequest.getUnitPrice();
        if (orderCreateRequest.getQuantity() >= wholesaleAmount) {
            unitPrice = unitPrice.subtract(unitPrice.multiply(wholesaleDiscount));
        }
        return unitPrice.setScale(2, RoundingMode.HALF_EVEN);
    }
}
