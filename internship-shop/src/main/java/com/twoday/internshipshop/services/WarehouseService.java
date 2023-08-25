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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class WarehouseService {

    private final RestTemplate restTemplate;

    private final PriceService priceService;

    private static final String PRODUCTS_ENDPOINT = "/products";

    private static final String ORDERS_ENDPOINT = "/orders";

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

    public String getReport(LocalDateTime startDateTime) {
        try {
            String url = ORDERS_ENDPOINT + "/reports/" + startDateTime;
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException exception) {
            ErrorMessage errorMessage = exception.getResponseBodyAs(ErrorMessage.class);
            if (errorMessage == null) {
                throw new UnknownException(exception.getMessage());
            }
            throw new BadRequestException(errorMessage.error());
        }
    }

    public ProductDTO getById(int id) {
        String url = String.format("%s/%s", PRODUCTS_ENDPOINT, id);
        ProductDTO productDTO = restTemplate.getForObject(url, ProductDTO.class);
        if (productDTO == null) return null;
        productDTO.setPrice(priceService.calculatePriceWithProfitMargin(productDTO.getPrice()));
        return productDTO;
    }

    public List<ProductDTO> getAllProducts() {
        ProductDTO[] productDTOS = restTemplate.getForObject(PRODUCTS_ENDPOINT, ProductDTO[].class);
        log.debug("Retrieved products:\n{}", Arrays.toString(productDTOS));

        if (productDTOS == null) return List.of();
        List<ProductDTO> productDtoList = List.of(productDTOS);
        productDtoList.forEach(productDTO ->
                productDTO.setPrice(priceService.calculatePriceWithProfitMargin(productDTO.getPrice())));
        log.debug("Products with updated prices:\n{}", productDtoList);

        return productDtoList;
    }

    public List<OrderDTO> getOrders() {
        OrderDTO[] orderDTOS = restTemplate.getForObject(ORDERS_ENDPOINT, OrderDTO[].class);
        log.debug("Retrieved orders:\n{}", Arrays.toString(orderDTOS));
        return orderDTOS == null
                ? List.of()
                : List.of(orderDTOS);
    }

    public List<OrderDTO> getOrdersByTimestampBetween(String startDateTime, String endDateTime) {
        String url = String.format("%s?%s=%s&%s=%s",
                ORDERS_ENDPOINT,
                "startDateTime",
                startDateTime,
                "endDateTime",
                endDateTime);
        OrderDTO[] orderDTOS = restTemplate.getForObject(url, OrderDTO[].class);
        log.debug("Retrieved orders with startDateTime {} & endDateTime {}:\n{}",
                startDateTime,
                endDateTime,
                Arrays.toString(orderDTOS));

        return orderDTOS == null
                ? List.of()
                : List.of(orderDTOS);
    }

    public OrderDTO createOrder(OrderCreateRequest orderCreateRequest) {
        orderCreateRequest.setUnitPrice(priceService.calculatePriceWithWholesaleDiscount(orderCreateRequest));
        log.debug("OrderCreateRequest with updated price:\n{}", orderCreateRequest);

        try {
            return restTemplate.postForObject(ORDERS_ENDPOINT, orderCreateRequest, OrderDTO.class);
        } catch (HttpClientErrorException exception) {
            ErrorMessage errorMessage = exception.getResponseBodyAs(ErrorMessage.class);
            if (errorMessage == null) {
                throw new UnknownException(exception.getMessage());
            }
            throw new BadRequestException(errorMessage.error());
        }
    }
}
