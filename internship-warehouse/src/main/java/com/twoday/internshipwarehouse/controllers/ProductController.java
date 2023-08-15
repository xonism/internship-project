package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id) {
        Product product = productService.getById(id);
        return new ResponseEntity<>(
                convertToDTO(product),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
        return new ResponseEntity<>(
                products,
                HttpStatus.OK);
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity());
    }
}
