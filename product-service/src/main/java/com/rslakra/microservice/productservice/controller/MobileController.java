package com.rslakra.microservice.productservice.controller;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import com.rslakra.microservice.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The HTTP service has resources in the following form:
 *
 * <pre>
 *  /{application}/{profile}[/{label}]
 *  /{application}-{profile}.yml
 *  /{label}/{application}-{profile}.yml
 *  /{application}-{profile}.properties
 *  /{label}/{application}-{profile}.properties
 * </pre>
 *
 * <pre>
 *  http://localhost:8116/actuator/refresh
 *  http://localhost:8888/actuator/health
 * </pre>
 *
 * @author Rohtash Lakra
 * @created 1/22/21 1:06 PM
 */
@RestController
@RequestMapping("/mobile/products")
public class MobileController {

    private final ProductService productService;

    @Autowired
    public MobileController(ProductService productService) {
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
