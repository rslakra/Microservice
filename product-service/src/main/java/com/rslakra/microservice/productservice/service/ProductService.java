package com.rslakra.microservice.productservice.service;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 3/2/24 2:45 PM
 */
@Service
public class ProductService {

    /**
     * @return
     */
    public List<Product> getAll() {
        return Arrays.asList(new Product());
    }

}
