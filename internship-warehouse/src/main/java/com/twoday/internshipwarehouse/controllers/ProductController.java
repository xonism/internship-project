package com.twoday.internshipwarehouse.controllers;

import com.twoday.internshipmodel.ProductDTO;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipmodel.ProductSellRequest;
import com.twoday.internshipwarehouse.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAll();
        return new ResponseEntity<>(
                products.stream().map(this::convertToDTO).toList(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> processProductSale(@RequestBody ProductSellRequest productSellRequest) {
        Product product = productService.processSale(productSellRequest);
        return new ResponseEntity<>(
                convertToDTO(product),
                HttpStatus.OK);
    }

    private ProductDTO convertToDTO(Product product) {
        return new ModelMapper().map(product, ProductDTO.class);
    }
}
