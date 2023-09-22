package com.twoday.internshipwarehouse.schedules;

import com.twoday.internshipwarehouse.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderReport {

    private final OrderService orderService;

    @Scheduled(cron = "${cron.order-report}")
    public void createOrderReport() throws IOException {
        // log time started
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1).truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        log.info("Creating report for orders made between {} and {}", startDateTime, endDateTime);

        orderService.createOrderReport(startDateTime, endDateTime);

        log.info("Order report created!");
    }
}
