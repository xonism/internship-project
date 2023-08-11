package com.twoday.internshipwarehouse.schedules;

import com.opencsv.CSVWriter;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderReport {

    private final OrderService orderService;

    @Scheduled(cron = "0 0 * * * *")
    public void createOrderReport() throws IOException {
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1).truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        log.info("Creating report for orders made between {} and {}", startDateTime, endDateTime);

        List<String[]> csvData = getCsvData(startDateTime, endDateTime);

        writeDataToCsvFile(csvData, startDateTime);
        log.info("Order report created!");
    }

    private List<String[]> getCsvData(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<String[]> csvData = new ArrayList<>();

        String[] headers = new String[]{"id", "userId", "productId", "quantity", "timestamp"};
        csvData.add(headers);

        orderService.getByTimestampBetween(startDateTime, endDateTime)
                .forEach(order -> {
                    String[] orderInfo = getOrderInfo(order);
                    csvData.add(orderInfo);
                });

        return csvData;
    }

    private String[] getOrderInfo(Order order) {
        return new String[]{String.valueOf(order.getId()),
                String.valueOf(order.getUser().getId()),
                String.valueOf(order.getProduct().getId()),
                String.valueOf(order.getQuantity()),
                String.valueOf(order.getTimestamp())};
    }

    private void writeDataToCsvFile(List<String[]> content, LocalDateTime startDateTime) throws IOException {
        String reportsDir = "../reports";
        //noinspection ResultOfMethodCallIgnored
        new File(reportsDir).mkdir();
        String fileName = String.format("%s/order-report-%s.csv", reportsDir, startDateTime)
                .replace(":", "-");

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            writer.writeAll(content);
        }
    }
}
