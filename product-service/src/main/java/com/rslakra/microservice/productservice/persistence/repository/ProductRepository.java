package com.rslakra.microservice.productservice.persistence.repository;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity.
 *
 * @author Rohtash Lakra
 * @created 3/2/24 2:53 PM
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds a product by SKU.
     *
     * @param sku the SKU to search for
     * @return the product with the given SKU, or null if not found
     */
    Product findBySku(String sku);

    /**
     * Finds a product by ISBN.
     *
     * @param isbn the ISBN to search for
     * @return the product with the given ISBN, or null if not found
     */
    Product findByIsbn(String isbn);

    /**
     * Checks if a product exists by name.
     *
     * @param name the product name
     * @return true if a product with the given name exists
     */
    boolean existsByName(String name);
}
