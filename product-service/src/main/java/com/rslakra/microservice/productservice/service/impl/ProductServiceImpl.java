package com.rslakra.microservice.productservice.service.impl;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import com.rslakra.microservice.productservice.persistence.repository.ProductRepository;
import com.rslakra.microservice.productservice.service.ProductService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:19 PM
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * @return
     */
    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Gets a product by ID.
     *
     * @param id the product ID
     * @return the product, or empty if not found
     */
    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Creates or updates a product.
     *
     * @param product the product to save
     * @return the saved product
     */
    @Override
    public Product save(Product product) {
        // Truncate fields to match database column lengths
        if (product.getName() != null) {
            product.setName(truncate(product.getName(), 64));
        }
        if (product.getBrand() != null) {
            product.setBrand(truncate(product.getBrand(), 64));
        }
        if (product.getDescription() != null) {
            product.setDescription(truncate(product.getDescription(), 1024));
        }
        if (product.getIsbn() != null) {
            product.setIsbn(truncate(product.getIsbn(), 64));
        }
        if (product.getSku() != null) {
            product.setSku(truncate(product.getSku(), 64));
        }
        if (product.getImageUrl() != null) {
            product.setImageUrl(truncate(product.getImageUrl(), 256));
        }

        LocalDateTime now = LocalDateTime.now();
        long timestamp = System.currentTimeMillis();
        String currentUser = "system"; // TODO: Get from security context

        if (product.getId() == null) {
            // New product - set created fields
            product.setCreatedOn(timestamp);
            product.setCreatedAt(now);
            product.setCreatedBy(currentUser);
        } else {
            // Update existing product - preserve original created fields
            Optional<Product> existingProduct = productRepository.findById(product.getId());
            if (existingProduct.isPresent()) {
                Product existing = existingProduct.get();
                // Preserve original creation audit fields
                product.setCreatedOn(existing.getCreatedOn());
                product.setCreatedAt(existing.getCreatedAt());
                product.setCreatedBy(existing.getCreatedBy());
            } else {
                // Product ID exists but not found - treat as new
                product.setCreatedOn(timestamp);
                product.setCreatedAt(now);
                product.setCreatedBy(currentUser);
            }
        }
        
        // Always update these fields
        product.setUpdatedOn(timestamp);
        product.setUpdatedAt(now);
        product.setUpdatedBy(currentUser);

        return productRepository.save(product);
    }

    /**
     * Deletes a product by ID.
     *
     * @param id the product ID
     */
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Checks if a product exists by name.
     *
     * @param name the product name
     * @return true if exists
     */
    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    /**
     * Imports products from CSV content.
     *
     * @param csvContent the CSV content as string
     * @return the number of products successfully imported
     * @throws Exception if CSV parsing fails
     */
    @Override
    public int importFromCsv(String csvContent) throws Exception {
        int importedCount = 0;
        LocalDateTime now = LocalDateTime.now();
        long timestamp = System.currentTimeMillis();
        String currentUser = "system"; // TODO: Get from security context

        try (StringReader reader = new StringReader(csvContent);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord record : csvParser) {
                try {
                    String name = record.get("name");
                    if (name == null || name.trim().isEmpty()) {
                        continue; // Skip rows without name
                    }

                    // Check if product already exists
                    if (existsByName(name)) {
                        continue; // Skip existing products
                    }

                    Product product = new Product();
                    product.setName(truncate(name.trim(), 64));
                    product.setBrand(truncate(getValueOrEmpty(record, "brand"), 64));
                    product.setDescription(truncate(getValueOrEmpty(record, "description"), 1024));
                    product.setIsbn(truncate(getValueOrEmpty(record, "isbn"), 64));
                    product.setSku(truncate(getValueOrEmpty(record, "sku"), 64));
                    product.setImageUrl(truncate(getValueOrEmpty(record, "image_url"), 256));

                    // Set audit fields
                    product.setCreatedOn(timestamp);
                    product.setCreatedAt(now);
                    product.setCreatedBy(currentUser);
                    product.setUpdatedOn(timestamp);
                    product.setUpdatedAt(now);
                    product.setUpdatedBy(currentUser);

                    productRepository.save(product);
                    importedCount++;
                } catch (Exception e) {
                    // Log error but continue processing other rows
                    LOGGER.warn("Error importing product from row {}: {}", record.getRecordNumber(), e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new Exception("Failed to parse CSV: " + e.getMessage(), e);
        }

        return importedCount;
    }

    /**
     * Gets a value from CSV record or returns empty string.
     *
     * @param record the CSV record
     * @param key    the column key
     * @return the value or empty string
     */
    private String getValueOrEmpty(CSVRecord record, String key) {
        try {
            String value = record.get(key);
            return value != null ? value.trim() : "";
        } catch (IllegalArgumentException e) {
            return ""; // Column doesn't exist
        }
    }

    /**
     * Truncates a string to the specified maximum length.
     * If the string is null or empty, returns empty string.
     * If the string exceeds maxLength, truncates it to maxLength.
     *
     * @param value     the string to truncate
     * @param maxLength the maximum length
     * @return the truncated string
     */
    private String truncate(String value, int maxLength) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }
}
