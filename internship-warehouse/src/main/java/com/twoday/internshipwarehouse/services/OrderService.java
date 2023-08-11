package com.twoday.internshipwarehouse.services;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ProductService productService;

    public List<Order> getByTimestampBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return orderRepository.findByTimestampBetween(startDateTime, endDateTime);
    }

    @Transactional
    public Order create(String username, OrderCreateRequest orderCreateRequest) {
        Product product = productService.updateQuantity(orderCreateRequest);
        User user = userService.getByUsername(username);
        Order order = Order.builder()
                .product(product)
                .user(user)
                .quantity(orderCreateRequest.quantity())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
        return orderRepository.save(order);
    }
}
