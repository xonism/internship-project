package com.twoday.internshipshop.utils;

import com.twoday.internshipmodel.OrderCreateRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PriceUtils {

    private static final BigDecimal PROFIT_MARGIN = BigDecimal.valueOf(0.2);

    private static final BigDecimal WHOLESALE_DISCOUNT = BigDecimal.valueOf(0.05);

    private static final int WHOLESALE_AMOUNT = 10;

    private PriceUtils() {

    }

    @PostConstruct
    private void postConstruct() {
        if (PROFIT_MARGIN.compareTo(WHOLESALE_DISCOUNT) <= 0) {
            throw new IllegalArgumentException("PROFIT_MARGIN has to be bigger than WHOLESALE_DISCOUNT");
        }
        if (PROFIT_MARGIN.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new IllegalArgumentException("PROFIT_MARGIN has to be bigger than 0");
        }
    }

    public static BigDecimal calculatePriceWithProfitMargin(BigDecimal warehousePrice) {
        return warehousePrice.add(warehousePrice.multiply(PROFIT_MARGIN))
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calculatePriceWithWholesaleDiscount(OrderCreateRequest orderCreateRequest) {
        BigDecimal unitPrice = orderCreateRequest.getUnitPrice();
        if (orderCreateRequest.getQuantity() >= WHOLESALE_AMOUNT) {
            unitPrice = unitPrice.subtract(unitPrice.multiply(WHOLESALE_DISCOUNT));
        }
        return unitPrice.setScale(2, RoundingMode.HALF_EVEN);
    }
}
