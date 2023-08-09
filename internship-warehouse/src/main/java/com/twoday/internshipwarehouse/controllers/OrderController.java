package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            Authentication authentication,
            @RequestBody OrderCreateRequest orderCreateRequest
    ) {
        Order order = orderService.create(authentication.getName(), orderCreateRequest);
        return new ResponseEntity<>(
                mapToDTO(order),
                HttpStatus.OK);
    }

    private OrderDTO mapToDTO(Order order) {
        return new OrderDTO(order.getId(), order.getUser().getId(), order.getProduct().getId(), order.getQuantity());
    }
}
