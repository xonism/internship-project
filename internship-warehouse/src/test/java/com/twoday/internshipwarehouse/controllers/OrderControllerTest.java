package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.ErrorMessage;
import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipwarehouse.TestHelpers;
import com.twoday.internshipwarehouse.models.Order;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.security.WebSecurityConfiguration;
import com.twoday.internshipwarehouse.services.OrderService;
import org.apache.commons.io.FileUtils;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest(value = OrderController.class)
@ExtendWith(MockitoExtension.class)
@Import(WebSecurityConfiguration.class)
@TestPropertySource(properties = {"directory.reports=" + TestHelpers.REPORTS_DIRECTORY})
class OrderControllerTest {

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
        BigDecimal unitPrice = new BigDecimal("1.1");

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(productId, quantity, unitPrice);

        String username = "user";
        String password = "password";

        Order order = Order.builder()
                .id(1)
                .user(new User(1, username, password))
                .product(new Product(1, "1", "1", unitPrice, 1))
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();

        when(orderService.create(username, orderCreateRequest)).thenReturn(order);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(endpoint)
                .content(TestHelpers.getObjectAsJsonString(orderCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        OrderDTO expectedResult = new OrderDTO(1, 1, productId, quantity, unitPrice);

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(TestHelpers.getObjectAsJsonString(expectedResult));

        verify(orderService).create(username, orderCreateRequest);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    @WithAnonymousUser
    void givenUnauthorizedUser_whenCreateOrderEndpointIsCalled_thenUnauthorizedStatusIsReturned() throws Exception {
        OrderCreateRequest orderCreateRequest =
                new OrderCreateRequest(1, 1, new BigDecimal("1.1"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(endpoint)
                .content(TestHelpers.getObjectAsJsonString(orderCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        verifyNoInteractions(orderService);
    }

    @Test
    @WithMockUser
    void givenValidLocalDateTime_whenDownloadOrderReportEndpointIsCalled_thenReportIsReturned() throws Exception {
        String url = String.format("%s/%s/%s", endpoint, "reports", TestHelpers.REPORT_LOCAL_DATE_TIME);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        File expectedResult = new File(TestHelpers.EXPECTED_ORDER_REPORT_PATH);

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(FileUtils.readFileToString(expectedResult, "UTF-8"));
    }

    @Test
    @WithMockUser
    void givenInvalidLocalDateTime_whenDownloadOrderReportEndpointIsCalled_thenDateTimeParseExceptionIsThrown()
            throws Exception {
        String url = String.format("%s/%s/%s", endpoint, "reports", "abc");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        ErrorMessage expectedResult = new ErrorMessage("Provided LocalDateTime 'abc' is invalid");

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(TestHelpers.getObjectAsJsonString(expectedResult));
    }

    @Test
    @WithMockUser
    void givenNonExistentLocalDateTime_whenDownloadOrderReportEndpointIsCalled_thenDateTimeParseExceptionIsThrown()
            throws Exception {
        String url = String.format("%s/%s/%s", endpoint, "reports", "2023-08-11T11:00");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        ErrorMessage expectedResult = new ErrorMessage("Requested file not found");

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(TestHelpers.getObjectAsJsonString(expectedResult));
    }
}
