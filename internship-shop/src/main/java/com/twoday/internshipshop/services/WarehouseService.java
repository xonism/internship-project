package com.twoday.internshipshop.services;

import com.twoday.internshipmodel.ErrorMessage;
import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipshop.exceptions.BadRequestException;
import com.twoday.internshipshop.exceptions.UnknownException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WarehouseService {

    private final RestTemplate restTemplate;

    public WarehouseService(
            @Value("${warehouse.username}") String username,
            @Value("${warehouse.password}") String password,
            @Value("${warehouse.url}") String warehouseUrl
    ) {
        this.restTemplate = new RestTemplateBuilder()
                .rootUri(warehouseUrl)
                .basicAuthentication(username, password)
                .build();
    }

    public List<ProductDTO> getAllProducts() {
        ProductDTO[] productDTOS = restTemplate.getForObject("/products", ProductDTO[].class);
        return productDTOS == null
                ? List.of()
                : List.of(productDTOS);
    }

    public OrderDTO createOrder(OrderCreateRequest orderCreateRequest) {
        try {
            return restTemplate.postForObject("/orders", orderCreateRequest, OrderDTO.class);
        } catch (HttpClientErrorException exception) {
            ErrorMessage errorMessage = exception.getResponseBodyAs(ErrorMessage.class);
            if (errorMessage == null) {
                throw new UnknownException(exception.getMessage());
            }
            throw new BadRequestException(errorMessage.error());
        }
    }
}
