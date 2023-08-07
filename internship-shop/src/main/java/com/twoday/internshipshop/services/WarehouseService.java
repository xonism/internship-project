package com.twoday.internshipshop.services;

import com.twoday.internshipmodel.ErrorMessage;
import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipmodel.ProductSellRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WarehouseService {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.warehouse_url}")
    private String warehouseUrl;

    public List<ProductDTO> getAllProducts() {
        ProductDTO[] productDTOS = getRestTemplate().getForObject(warehouseUrl, ProductDTO[].class);
        return productDTOS == null
                ? List.of()
                : List.of(productDTOS);
    }

    public Object processSale(ProductSellRequest productSellRequest) {
        try {
            return getRestTemplate().postForObject(warehouseUrl, productSellRequest, ProductDTO.class);
        } catch (HttpClientErrorException exception) {
            ErrorMessage errorMessage = exception.getResponseBodyAs(ErrorMessage.class);
            if (errorMessage == null) {
                throw exception;
            }
            return errorMessage;
        }
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .basicAuthentication(username, password)
                .build();
    }
}
