package com.twoday.internshipwarehouse.services;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipwarehouse.TestHelpers;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.repositories.OrderRepository;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Test
    void givenValidOrderCreateRequest_whenCreateOrder_thenOrderIsCreated() {
        int id = 1;
        BigDecimal unitPrice = new BigDecimal("1.1");
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(id, 1, unitPrice);
        Product product = new Product(id, "1", "1", unitPrice, 1);
        when(productService.updateQuantity(orderCreateRequest)).thenReturn(product);

        String username = "user";
        User user = new User(id, username, "password");
        when(userService.getByUsername(username)).thenReturn(user);

        when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        Order actualResult = orderService.create(username, orderCreateRequest);
        Order expectedResult = Order.builder()
                .id(0)
                .user(user)
                .product(product)
                .quantity(1)
                .unitPrice(unitPrice)
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(productService).updateQuantity(orderCreateRequest);
        verifyNoMoreInteractions(productService);

        verify(userService).getByUsername(username);
        verifyNoMoreInteractions(userService);

        verify(orderRepository).save(Mockito.any(Order.class));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void whenCreateOrderReport_thenOrderReportIsCreated() throws IOException {
        ReflectionTestUtils.setField(orderService, "reportsDirectory", TestHelpers.REPORTS_DIRECTORY);

        BigDecimal unitPrice = new BigDecimal("1.1");

        User user = new User(1, "user", "password");

        int quantity = 1;
        Product product = new Product(1, "1", "1", unitPrice, quantity);

        LocalDateTime localDateTime = LocalDateTime.parse(TestHelpers.REPORT_LOCAL_DATE_TIME);

        List<Order> orders = List.of(
                Order.builder()
                        .id(1)
                        .user(user)
                        .product(product)
                        .quantity(quantity)
                        .unitPrice(unitPrice)
                        .timestamp(localDateTime.plusMinutes(30).truncatedTo(ChronoUnit.SECONDS))
                        .build(),
                Order.builder()
                        .id(2)
                        .user(user)
                        .product(product)
                        .quantity(quantity)
                        .unitPrice(unitPrice)
                        .timestamp(localDateTime.plusMinutes(45).truncatedTo(ChronoUnit.SECONDS))
                        .build());

        LocalDateTime startDateTime = localDateTime.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDateTime = localDateTime.plusHours(1).truncatedTo(ChronoUnit.HOURS);

        when(orderRepository.findByTimestampBetween(startDateTime, endDateTime))
                .thenReturn(orders);

        orderService.createOrderReport(startDateTime, endDateTime);

        String orderReportFilePath = com.twoday.internshipwarehouse.utils.FileUtils.getOrderReportFilePath(
                TestHelpers.REPORTS_DIRECTORY,
                startDateTime);
        File actualResult = new File(orderReportFilePath);
        File expectedResult = new File(TestHelpers.EXPECTED_ORDER_REPORT_PATH);
        assertThat(FileUtils.contentEquals(actualResult, expectedResult)).isTrue();

        verify(orderRepository).findByTimestampBetween(startDateTime, endDateTime);
        verifyNoMoreInteractions(orderRepository);
    }
}
