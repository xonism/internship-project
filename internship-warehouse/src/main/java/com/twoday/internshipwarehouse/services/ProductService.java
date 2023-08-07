package com.twoday.internshipwarehouse.services;

import com.twoday.internshipmodel.ProductSellRequest;
import com.twoday.internshipwarehouse.constants.Constants;
import com.twoday.internshipwarehouse.exceptions.InsufficientQuantityException;
import com.twoday.internshipwarehouse.exceptions.InvalidValueException;
import com.twoday.internshipwarehouse.exceptions.ProductNotFoundByIdException;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product processSale(ProductSellRequest productSellRequest) {
        if (productSellRequest.quantity() <= 0) {
            throw new InvalidValueException(Constants.QUANTITY);
        }

        Product product = productRepository.findById((long) productSellRequest.id()).orElse(null);

        if (product == null) {
            throw new ProductNotFoundByIdException(productSellRequest.id());
        }

        if (product.getQuantity() < productSellRequest.quantity()) {
            throw new InsufficientQuantityException(productSellRequest.id());
        }

        int newQuantity = product.getQuantity() - productSellRequest.quantity();
        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }
}
