package com.rslakra.microservice.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A client that both registers itself with the registry and uses the Spring Cloud DiscoveryClient abstraction to
 * interrogate the registry for its own host and port.
 *
 * @author Rohtash Lakra
 * @created 1/21/21 3:21 PM
 */
//@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
}
