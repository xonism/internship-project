package com.twoday.warehouse.services;

import com.twoday.model.models.Product;
import com.twoday.model.records.ProductSellRequest;
import com.twoday.warehouse.constants.Constants;
import com.twoday.warehouse.exceptions.InsufficientQuantityException;
import com.twoday.warehouse.exceptions.InvalidValueException;
import com.twoday.warehouse.exceptions.ProductNotFoundByIdException;
import com.twoday.warehouse.repositories.WarehouseRepository;
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
