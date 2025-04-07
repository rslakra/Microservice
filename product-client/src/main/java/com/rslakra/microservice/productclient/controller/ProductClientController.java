package com.rslakra.microservice.productclient.controller;

import com.rslakra.microservice.productclient.proxy.ApiProxy;
import com.rslakra.microservice.productservice.persistence.entity.Product;
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
@RequestMapping("/api/products")
public class ProductClientController {

    private final ApiProxy apiProxy;

    /**
     * @param apiProxy
     */
    @Autowired
    public ProductClientController(ApiProxy apiProxy) {
        this.apiProxy = apiProxy;
    }

    /**
     * @return
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return apiProxy.getAllProducts();
    }

}
