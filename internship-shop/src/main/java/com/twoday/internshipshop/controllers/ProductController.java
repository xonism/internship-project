package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipshop.services.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final WarehouseService warehouseService;

    public ProductController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(
                warehouseService.getAllProducts(),
                HttpStatus.OK);
    }
}
