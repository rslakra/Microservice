package com.rslakra.microservice.productservice.service;

import com.rslakra.microservice.productservice.persistence.entity.Product;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 3/2/24 2:45 PM
 */

public interface ProductService {

    /**
     * @return
     */
    public List<Product> getAll();

}
