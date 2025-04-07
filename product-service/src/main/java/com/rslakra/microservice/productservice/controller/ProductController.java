package com.rslakra.microservice.productservice.controller;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import com.rslakra.microservice.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 10/25/22 1:56 PM
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * @return
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

}
