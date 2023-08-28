package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipshop.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final WarehouseService warehouseService;

    @GetMapping(value = "/reports/{localDateTime}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> downloadOrderReport(@PathVariable String localDateTime) {
        log.info("Download order report endpoint called with value: {}", localDateTime);

        LocalDateTime startDateTime = LocalDateTime.parse(localDateTime).truncatedTo(ChronoUnit.HOURS);
        String reportData = warehouseService.getReport(startDateTime);
        log.debug("Report data retrieved: {}", reportData);

        return new ResponseEntity<>(
                reportData,
                getContentDispositionHeaders(startDateTime),
                HttpStatus.OK);
    }

    @GetMapping
    public List<OrderDTO> getOrders(@RequestParam(required = false) String startDateTime,
                                    @RequestParam(required = false) String endDateTime) {
        log.info("Get orders endpoint called");

        return startDateTime != null && endDateTime != null
                ? warehouseService.getOrdersByTimestampBetween(startDateTime, endDateTime)
                : warehouseService.getOrders();
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        log.info("Create order endpoint called");
        log.debug("OrderCreateRequest received:\n{}", orderCreateRequest);

        OrderDTO orderDTO = warehouseService.createOrder(orderCreateRequest);
        log.debug("Order created:\n{}", orderDTO);

        return orderDTO;
    }

    private MultiValueMap<String, String> getContentDispositionHeaders(LocalDateTime startDateTime) {
        String fileName = String.format("%s-%s.%s",
                "order-report",
                startDateTime.toString().replace(":", "-"),
                "csv");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String contentDispositionHeaderValue = String.format("attachment; filename=\"%s\"", fileName);
        headers.put(HttpHeaders.CONTENT_DISPOSITION, List.of(contentDispositionHeaderValue));
        return headers;
    }
}
