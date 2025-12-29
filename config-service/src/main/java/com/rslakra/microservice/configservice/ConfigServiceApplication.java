package com.rslakra.microservice.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config Server Application.
 * <p>
 * <code>@EnableConfigServer</code> annotation enables the Spring Cloud Config Server.
 * <code>@EnableDiscoveryClient</code> annotation registers this service with Eureka Server.
 *
 * @author Rohtash Lakra
 * @created 10/28/22 3:04 PM
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServiceApplication {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}
