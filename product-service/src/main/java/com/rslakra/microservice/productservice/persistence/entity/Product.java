package com.rslakra.microservice.productservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * https://schema.org/Product
 *
 * @author Rohtash Lakra
 * @created 3/2/24 2:46 PM
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product extends Auditable {

    // The local id of the product.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    
    // The name of the product.
    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;
    
    // The brand of the product.
    @Column(name = "brand", length = 64)
    private String brand;
    
    // The product description.
    @Column(name = "description", length = 1024)
    private String description;
    
    // Include all applicable global identifiers as described in schema.org/Product
    @Column(name = "isbn", nullable = false, length = 64)
    private String isbn;
    
    // The merchant-specific identifier for the product.
    @Column(name = "sku", nullable = false, length = 64)
    private String sku;
    
    // A picture clearly showing the product. Must be in .jpg, .png, or. gif format.
    @Column(name = "image_url", length = 256)
    private String imageUrl;

}
