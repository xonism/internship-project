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

    @Value("${warehouse.username}")
    private String username;

    @Value("${warehouse.password}")
    private String password;

    @Value("${warehouse.url}")
    private String warehouseUrl;

    public List<ProductDTO> getAllProducts() {
        String url = warehouseUrl + "/products";
        ProductDTO[] productDTOS = getRestTemplate().getForObject(url, ProductDTO[].class);
        return productDTOS == null
                ? List.of()
                : List.of(productDTOS);
    }

    public OrderDTO createOrder(OrderCreateRequest orderCreateRequest) {
        String url = warehouseUrl + "/orders";

        try {
            return getRestTemplate().postForObject(url, orderCreateRequest, OrderDTO.class);
        } catch (HttpClientErrorException exception) {
            ErrorMessage errorMessage = exception.getResponseBodyAs(ErrorMessage.class);
            if (errorMessage == null) {
                throw new UnknownException(exception.getMessage());
            }
            throw new BadRequestException(errorMessage.error());
        }
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .basicAuthentication(username, password)
                .build();
    }
}
