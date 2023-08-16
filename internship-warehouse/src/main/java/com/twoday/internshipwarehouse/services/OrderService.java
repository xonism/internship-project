package com.twoday.internshipwarehouse.services;

import com.opencsv.CSVWriter;
import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.repositories.OrderRepository;
import com.twoday.internshipwarehouse.utils.FileUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    @Value("${directory.reports}")
    private String reportsDirectory;

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ProductService productService;

    @PostConstruct
    private void postConstruct() {
        //noinspection ResultOfMethodCallIgnored
        new File(reportsDirectory).mkdir();
    }

    @Transactional
    public Order create(String username, OrderCreateRequest orderCreateRequest) {
        Product product = productService.updateQuantity(orderCreateRequest);
        log.debug("Product updated:\n{}", product);

        User user = userService.getByUsername(username);
        Order order = Order.builder()
                .product(product)
                .user(user)
                .quantity(orderCreateRequest.getQuantity())
                .unitPrice(orderCreateRequest.getUnitPrice())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
        return orderRepository.save(order);
    }

    public void createOrderReport(LocalDateTime startDateTime, LocalDateTime endDateTime) throws IOException {
        List<String[]> csvData = getCsvData(startDateTime, endDateTime);

        String orderReportFilePath = FileUtils.getOrderReportFilePath(reportsDirectory, startDateTime);
        FileWriter fileWriter = new FileWriter(orderReportFilePath);

        log.debug("Writing csvData to {}:\n{}",
                orderReportFilePath,
                csvData.stream().map(Arrays::toString).toList());

        try (CSVWriter csvWriter = new CSVWriter(fileWriter)) {
            csvWriter.writeAll(csvData);
        }
    }

    private List<String[]> getCsvData(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<String[]> csvData = new ArrayList<>();

        String[] headers = new String[]{"id", "userId", "productId", "quantity", "unitPrice", "timestamp"};
        csvData.add(headers);

        getByTimestampBetween(startDateTime, endDateTime)
                .forEach(order -> csvData.add(getOrderInfo(order)));

        return csvData;
    }

    private List<Order> getByTimestampBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return orderRepository.findByTimestampBetween(startDateTime, endDateTime);
    }

    private String[] getOrderInfo(Order order) {
        return new String[]{String.valueOf(order.getId()),
                String.valueOf(order.getUser().getId()),
                String.valueOf(order.getProduct().getId()),
                String.valueOf(order.getQuantity()),
                String.valueOf(order.getUnitPrice()),
                String.valueOf(order.getTimestamp())};
    }
}
