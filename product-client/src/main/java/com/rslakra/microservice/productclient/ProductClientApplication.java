package com.rslakra.microservice.productclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * This application will perform as consumer of APIs which is written in the main server. It consumes the APIs from the
 * both main server instance based on availability through load balancer.
 * <p>
 * We also use <code>netflix-eureka-client</code> library to communicate with load balancer application.
 * <p>
 * OpenFeign
 * <p>
 * We are using <code>OpenFeign</code> to consume APIs rather than using traditional HTTP libraries.
 * <code>OpenFeign</code>  will act as a proxy in between server and client.
 * <p>
 * After running the client application, an instance of this application also appears in the Eureka server dashboard.
 *
 * @author Rohtash Lakra
 * @created 10/28/22 3:04 PM
 */
@EnableFeignClients("com.rslakra.microservice.productclient")
@EnableDiscoveryClient
@SpringBootApplication
public class ProductClientApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductClientApplication.class, args);
    }
}
