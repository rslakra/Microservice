package com.rslakra.microservice.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Eureka Client Registry.
 * <p>
 * <code>@EnableDiscoveryClient</code> named annotation used to register the application with Eureka server in the main
 * class.
 *
 * <code>@EnableZuulProxy</code> named annotation used to connect the Zuul library.
 *
 * @author Rohtash Lakra
 * @created 1/21/21 2:48 PM
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiGatewayApplication.class);


    /**
     * @param args
     */
    public static void main(String[] args) {
        LOGGER.info("Started ApiGateway Application ...");
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
