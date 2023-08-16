package com.twoday.internshipmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private int id;

    private String name;

    private String description;

    private BigDecimal price;

    private int quantity;
}
