package com.rslakra.microservice.productservice.service.impl;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import com.rslakra.microservice.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:19 PM
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * @return
     */
    @Override
    public List<Product> getAll() {
        return Arrays.asList(new Product());
    }
}
