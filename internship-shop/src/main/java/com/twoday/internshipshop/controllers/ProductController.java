package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipshop.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Get all products endpoint called");

        return new ResponseEntity<>(
                warehouseService.getAllProducts(),
                HttpStatus.OK);
    }
}
