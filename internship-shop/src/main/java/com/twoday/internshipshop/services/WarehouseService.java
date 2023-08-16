package com.twoday.internshipshop.services;

import com.twoday.internshipmodel.ErrorMessage;
import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipmodel.OrderDTO;
import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipshop.exceptions.BadRequestException;
import com.twoday.internshipshop.exceptions.UnknownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class WarehouseService {

    private final RestTemplate restTemplate;

    private final PriceService priceService;

    public WarehouseService(
            @Value("${warehouse.username}") String username,
            @Value("${warehouse.password}") String password,
            @Value("${warehouse.url}") String warehouseUrl,
            PriceService priceService) {
        this.restTemplate = new RestTemplateBuilder()
                .rootUri(warehouseUrl)
                .basicAuthentication(username, password)
                .build();
        this.priceService = priceService;
    }

    public ProductDTO getById(int id) {
        return restTemplate.getForObject("/products/" + id, ProductDTO.class);
    }

    public List<ProductDTO> getAllProducts() {
        ProductDTO[] productDTOS = restTemplate.getForObject("/products", ProductDTO[].class);
        if (productDTOS == null) return List.of();
        List<ProductDTO> productDtoList = List.of(productDTOS);
        productDtoList.forEach(productDTO ->
                productDTO.setPrice(priceService.calculatePriceWithProfitMargin(productDTO.getPrice())));
        return productDtoList;
    }

    public OrderDTO createOrder(OrderCreateRequest orderCreateRequest) {
        orderCreateRequest.setUnitPrice(priceService.calculatePriceWithWholesaleDiscount(orderCreateRequest));
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
