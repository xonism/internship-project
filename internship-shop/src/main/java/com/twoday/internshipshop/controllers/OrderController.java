package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipshop.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        log.info("Create order endpoint called");
        log.debug("OrderCreateRequest received:\n{}", orderCreateRequest);

        OrderDTO orderDTO = warehouseService.createOrder(orderCreateRequest);
        log.debug("Order created:\n{}", orderDTO);

        return new ResponseEntity<>(
                orderDTO,
                HttpStatus.OK);
    }
}
