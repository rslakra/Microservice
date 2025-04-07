package com.rslakra.microservice.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <code>@EnableDiscoveryClient</code> named annotation to register the application with eureka server.
 *
 * @author Rohtash Lakra
 * @created 10/28/22 3:04 PM
 */
@EnableDiscoveryClient
@EnableJpaRepositories
@SpringBootApplication
public class ProductServiceApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
