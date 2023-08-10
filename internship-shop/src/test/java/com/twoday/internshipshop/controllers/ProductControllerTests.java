package com.twoday.internshipshop.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipshop.services.WarehouseService;
import com.twoday.internshipshop.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest(value = ProductController.class)
@ExtendWith(MockitoExtension.class)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarehouseService warehouseService;

    @Test
    void givenValidListOfProductDTOS_whenCallGetAllProductsEndpoint_ThenAllProductsAreReturned() throws Exception {
        List<ProductDTO> products = List.of(
                new ProductDTO(1, "Item 1", "Item 1", BigDecimal.valueOf(1.11), 1),
                new ProductDTO(2, "Item 2", "Item 2", BigDecimal.valueOf(2.22), 2)
        );

        when(warehouseService.getAllProducts()).thenReturn(products);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products");

        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(JsonUtils.getObjectAsJsonString(products));

        verify(warehouseService).getAllProducts();
        verifyNoMoreInteractions(warehouseService);
    }
}
