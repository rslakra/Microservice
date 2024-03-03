package com.rslakra.microservice.productservice.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * https://schema.org/Product
 *
 * @author Rohtash Lakra
 * @created 3/2/24 2:46 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class Product {

    // The local id of the product.
    private Long id;
    // The name of the product.
    private String name;
    // The brand of the product.
    private String brand;
    // The product description.
    private String description;
    // Include all applicable global identifiers as described in schema.org/Product
    private String isbn;
    // The merchant-specific identifier for the product.
    private String sku;
    // A picture clearly showing the projecty. Must be in .jpg, .png, or. gif format.
    private String imageUrl;

}
