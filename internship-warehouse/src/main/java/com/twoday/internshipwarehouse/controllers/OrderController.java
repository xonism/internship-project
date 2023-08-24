package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.services.OrderService;
import com.twoday.internshipwarehouse.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Value("${directory.reports}")
    private String reportsDirectory;

    private final OrderService orderService;

    @GetMapping(value = "/reports/{localDateTime}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<FileSystemResource> downloadOrderReport(@PathVariable String localDateTime) {
        log.info("Download order report endpoint called with value: {}", localDateTime);

        LocalDateTime startDateTime = LocalDateTime.parse(localDateTime).truncatedTo(ChronoUnit.HOURS);
        File file = new File(FileUtils.getOrderReportFilePath(reportsDirectory, startDateTime));
        log.debug("File retrieved: {}", file.getPath());

        return new ResponseEntity<>(
                new FileSystemResource(file),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            Authentication authentication,
            @RequestBody OrderCreateRequest orderCreateRequest
    ) {
        log.info("Create order endpoint called");

        log.debug("OrderCreateRequest received:\n{}", orderCreateRequest);
        Order order = orderService.create(authentication.getName(), orderCreateRequest);
        log.debug("Order created:\n{}", order);

        return new ResponseEntity<>(
                mapToDTO(order),
                HttpStatus.OK);
    }

    private OrderDTO mapToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getProduct().getId(),
                order.getQuantity(),
                order.getUnitPrice());
    }
}
