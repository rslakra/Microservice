package com.rslakra.microservice.productservice.service;

import com.rslakra.microservice.productservice.persistence.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 3/2/24 2:45 PM
 */
public interface ProductService {

    /**
     * Gets all products.
     *
     * @return list of all products
     */
    List<Product> getAll();

    /**
     * Gets a product by ID.
     *
     * @param id the product ID
     * @return the product, or empty if not found
     */
    Optional<Product> getById(Long id);

    /**
     * Creates or updates a product.
     *
     * @param product the product to save
     * @return the saved product
     */
    Product save(Product product);

    /**
     * Deletes a product by ID.
     *
     * @param id the product ID
     */
    void deleteById(Long id);

    /**
     * Checks if a product exists by name.
     *
     * @param name the product name
     * @return true if exists
     */
    boolean existsByName(String name);

    /**
     * Imports products from CSV content.
     *
     * @param csvContent the CSV content as string
     * @return the number of products successfully imported
     * @throws Exception if CSV parsing fails
     */
    int importFromCsv(String csvContent) throws Exception;
}
