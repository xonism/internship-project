package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipshop.TestHelpers;
import com.twoday.internshipshop.services.WarehouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest(value = OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseService warehouseService;

    @Test
    void givenValidOrderCreateRequest_whenCallCreateOrderEndpoint_ThenCreatedOrderIsReturned() throws Exception {
        int productId = 1;
        int quantity = 1;

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(productId, quantity);

        OrderDTO orderDTO = new OrderDTO(1, 1, productId, quantity);

        when(warehouseService.createOrder(orderCreateRequest)).thenReturn(orderDTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
                .content(TestHelpers.getObjectAsJsonString(orderCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(TestHelpers.getObjectAsJsonString(orderDTO));

        verify(warehouseService).createOrder(orderCreateRequest);
        verifyNoMoreInteractions(warehouseService);
    }
}
