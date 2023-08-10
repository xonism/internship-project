package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipwarehouse.TestHelpers;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.security.WebSecurityConfiguration;
import com.twoday.internshipwarehouse.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest(value = ProductController.class)
@ExtendWith(MockitoExtension.class)
@Import(WebSecurityConfiguration.class)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String endpoint = "/products";

    @Test
    @WithMockUser
    void givenAuthorizedUser_whenGetAllProducts_thenProductsAreReturned() throws Exception {
        List<Product> products = List.of(
                new Product(1, "Item 1", "Item 1", BigDecimal.valueOf(1.11), 1),
                new Product(2, "Item 2", "Item 2", BigDecimal.valueOf(2.22), 2)
        );
        when(productService.getAll()).thenReturn(products);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint);
        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        List<ProductDTO> expectedResult = products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity()))
                .toList();

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(actualResult.getResponse().getContentAsString())
                .isEqualTo(TestHelpers.getObjectAsJsonString(expectedResult));

        verify(productService).getAll();
        verifyNoMoreInteractions(productService);
    }

    @Test
    @WithAnonymousUser
    void givenUnauthorizedUser_whenGetAllProducts_thenUnauthorizedStatusIsReturned() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint);

        MvcResult actualResult = mockMvc.perform(requestBuilder).andReturn();

        assertThat(actualResult.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        verifyNoInteractions(productService);
    }
}
