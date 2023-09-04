package com.rslakra.microservice.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Service Registry.
 * <p>
 * The <code>@EnableEurekaServer</code> annotation is added to enable the service registry.
 *
 * By default, the registry also tries to register itself, so you need to disable that behavior as well.
 *
 * @author Rohtash Lakra
 * @created 1/21/21 2:48 PM
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
    }

}
