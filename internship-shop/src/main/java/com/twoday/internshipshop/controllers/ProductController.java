package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipshop.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final WarehouseService warehouseService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        log.info("Get product by ID {} endpoint called", id);

        ProductDTO product = warehouseService.getById(id);
        return new ResponseEntity<>(
                product,
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Get all products endpoint called");

        return new ResponseEntity<>(
                warehouseService.getAllProducts(),
                HttpStatus.OK);
    }
}
