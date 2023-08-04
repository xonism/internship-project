package com.twoday.internshipwarehouse.repositories;

import com.twoday.internshipmodel.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WarehouseRepository {

    private final List<Product> products = List.of(
            new Product(1, "Item 1", "Item 1", new BigDecimal("1.11"), 1),
            new Product(2, "Item 2", "Item 2", new BigDecimal("2.22"), 2),
            new Product(3, "Item 3", "Item 3", new BigDecimal("3.33"), 3));

    public List<Product> findAll() {
        return products;
    }

    public Product findById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
