package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.security.WebSecurityConfiguration;
import com.twoday.internshipwarehouse.services.OrderService;
import com.twoday.internshipwarehouse.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest(value = OrderController.class)
@ExtendWith(MockitoExtension.class)
@Import(WebSecurityConfiguration.class)
class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private final String endpoint = "/orders";

    @Test
    @WithMockUser
    void givenAuthorizedUser_whenCreateOrderEndpointIsCalled_thenOrderIsReturned() throws Exception {
        int productId = 1;
        int quantity = 1;

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(productId, quantity);

        String username = "user";
        String password = "password";

        Order order = Order.builder()
                .id(1)
                .user(new User(1, username, password))
                .product(new Product(1, "1", "1", new BigDecimal("1.1"), 1))
                .quantity(quantity)
                .build();

        when(orderService.create(username, orderCreateRequest)).thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(endpoint)
                .content(JsonUtils.getObjectAsJsonString(orderCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        OrderDTO expectedResult = new OrderDTO(1, 1, productId, quantity);

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(JsonUtils.getObjectAsJsonString(expectedResult));

        verify(orderService).create(username, orderCreateRequest);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    @WithAnonymousUser
    void givenUnauthorizedUser_whenCreateOrderEndpointIsCalled_thenUnauthorizedStatusIsReturned() throws Exception {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(1, 1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(endpoint)
                .content(JsonUtils.getObjectAsJsonString(orderCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        verifyNoInteractions(orderService);
    }
}
