package com.twoday.internshipmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreateRequest {

    private int productId;

    private int quantity;

    private BigDecimal unitPrice;
}
