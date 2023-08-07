package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipmodel.ProductSellRequest;
import com.twoday.internshipshop.services.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ShopController {

    private final WarehouseService warehouseService;

    public ShopController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(
                warehouseService.getAllProducts(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> processProductSale(@RequestBody ProductSellRequest productSellRequest) {
        return new ResponseEntity<>(
                warehouseService.processSale(productSellRequest),
                HttpStatus.OK);
    }
}
