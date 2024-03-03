package com.rslakra.microservice.eurekaservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Service Registry.
 * <p>
 * The <code>@EnableEurekaServer</code> annotation is added to enable the service registry.
 * <p>
 * By default, the registry also tries to register itself, so you need to disable that behavior as well.
 *
 * <code>@EnableEurekaServer</code> named annotation will allow the eureka server to control this application.
 *
 * @author Rohtash Lakra
 * @created 1/21/21 2:48 PM
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaServiceApplication.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        LOGGER.info("Started Eureka/Registry Service Application ...");
        SpringApplication.run(EurekaServiceApplication.class, args);
    }

}
