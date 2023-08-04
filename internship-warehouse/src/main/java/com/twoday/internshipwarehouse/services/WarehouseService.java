package com.twoday.internshipwarehouse.services;

import com.twoday.internshipmodel.Product;
import com.twoday.internshipmodel.ProductSellRequest;
import com.twoday.internshipwarehouse.constants.Constants;
import com.twoday.internshipwarehouse.exceptions.InsufficientQuantityException;
import com.twoday.internshipwarehouse.exceptions.InvalidValueException;
import com.twoday.internshipwarehouse.exceptions.ProductNotFoundByIdException;
import com.twoday.internshipwarehouse.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public List<Product> getAll() {
        return warehouseRepository.findAll();
    }

    public Product processSale(ProductSellRequest productSellRequest) {
        if (productSellRequest.quantity() <= 0) {
            throw new InvalidValueException(Constants.QUANTITY);
        }

        Product product = warehouseRepository.findById(productSellRequest.id());

        if (product == null) {
            throw new ProductNotFoundByIdException(productSellRequest.id());
        }

        if (product.getQuantity() < productSellRequest.quantity()) {
            throw new InsufficientQuantityException(productSellRequest.id());
        }

        int newQuantity = product.getQuantity() - productSellRequest.quantity();
        product.setQuantity(newQuantity);
        return product;
    }
}
