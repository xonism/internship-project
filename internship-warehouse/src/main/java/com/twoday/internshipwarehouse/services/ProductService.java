package com.twoday.internshipwarehouse.services;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipwarehouse.constants.Constants;
import com.twoday.internshipwarehouse.exceptions.InsufficientQuantityException;
import com.twoday.internshipwarehouse.exceptions.InvalidValueException;
import com.twoday.internshipwarehouse.exceptions.ProductNotFoundByIdException;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product getById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundByIdException(id));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product updateQuantity(OrderCreateRequest orderCreateRequest) {
        if (orderCreateRequest.getQuantity() <= 0) {
            throw new InvalidValueException(Constants.QUANTITY);
        }

        Product product = getById(orderCreateRequest.getProductId());

        if (product.getQuantity() < orderCreateRequest.getQuantity()) {
            throw new InsufficientQuantityException(orderCreateRequest.getProductId());
        }

        int newQuantity = product.getQuantity() - orderCreateRequest.getQuantity();
        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }
}
