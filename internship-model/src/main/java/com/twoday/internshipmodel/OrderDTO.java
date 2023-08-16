package com.twoday.internshipmodel;

import java.math.BigDecimal;

public record OrderDTO(int id, int userId, int productId, int quantity, BigDecimal unitPrice) {

}
