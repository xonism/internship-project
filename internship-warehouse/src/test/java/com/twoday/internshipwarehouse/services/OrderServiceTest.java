package com.twoday.internshipwarehouse.services;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(id, 1);
        Product product = new Product(id, "1", "1", new BigDecimal("1.1"), 1);
        when(productService.updateQuantity(orderCreateRequest)).thenReturn(product);

        String username = "user";
        User user = new User(id, username, "password");
        when(userService.getByUsername(username)).thenReturn(user);

        when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        Order actualResult = orderService.create(username, orderCreateRequest);
        Order expectedResult = new Order(
                0,
                user,
                product,
                1,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(productService).updateQuantity(orderCreateRequest);
        verifyNoMoreInteractions(productService);

        verify(userService).getByUsername(username);
        verifyNoMoreInteractions(userService);

        verify(orderRepository).save(Mockito.any(Order.class));
        verifyNoMoreInteractions(orderRepository);
    }
}
