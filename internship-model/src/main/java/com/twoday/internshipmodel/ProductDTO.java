package com.twoday.internshipmodel;

import java.math.BigDecimal;

public record ProductDTO(int id, String name, String description, BigDecimal price, int quantity) {

}
