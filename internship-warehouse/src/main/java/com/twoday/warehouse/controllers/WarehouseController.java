package com.twoday.warehouse.controllers;

import com.twoday.model.Product;
import com.twoday.model.ProductSellRequest;
import com.twoday.warehouse.services.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(
                warehouseService.getAll(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> processProductSale(@RequestBody ProductSellRequest productSellRequest) {
        return new ResponseEntity<>(
                warehouseService.processSale(productSellRequest),
                HttpStatus.OK);
    }
}
