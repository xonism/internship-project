package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipshop.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        return new ResponseEntity<>(
                warehouseService.createOrder(orderCreateRequest),
                HttpStatus.OK);
    }
}
