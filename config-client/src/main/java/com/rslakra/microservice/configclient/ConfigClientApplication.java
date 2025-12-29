package com.rslakra.microservice.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Config Client Application.
 * <p>
 * <code>@EnableDiscoveryClient</code> annotation registers this service with Eureka Server.
 *
 * @author Rohtash Lakra
 * @created 10/28/22 3:04 PM
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
