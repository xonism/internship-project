package com.twoday.internshipmodel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(int id, int userId, int productId, int quantity, BigDecimal unitPrice, LocalDateTime timestamp) {

}
