package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.services.OrderService;
import com.twoday.internshipwarehouse.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/reports/{startDateTime}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadOrderReport(@PathVariable String localDateTime) {
        LocalDateTime startDateTime = LocalDateTime.parse(localDateTime).truncatedTo(ChronoUnit.HOURS);
        File file = new File(FileUtils.getOrderReportFilePath(startDateTime));

        return new ResponseEntity<>(
                new FileSystemResource(file),
                getContentDispositionHeaders(file.getName()),
                HttpStatus.OK);
    }

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

    private MultiValueMap<String, String> getContentDispositionHeaders(String fileName) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        String contentDispositionHeaderValue = String.format("attachment; filename=\"%s\"", fileName);
        headers.put(HttpHeaders.CONTENT_DISPOSITION, List.of(contentDispositionHeaderValue));
        return headers;
    }
}
