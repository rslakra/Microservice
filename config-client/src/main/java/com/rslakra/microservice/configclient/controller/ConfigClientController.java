package com.rslakra.microservice.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The HTTP service has resources in the following form:
 *
 * <pre>
 *  /{application}/{profile}[/{label}]
 *  /{application}-{profile}.yml
 *  /{label}/{application}-{profile}.yml
 *  /{application}-{profile}.properties
 *  /{label}/{application}-{profile}.properties
 * </pre>
 *
 * <pre>
 *  http://localhost:8116/actuator/refresh
 *  http://localhost:8888/actuator/health
 * </pre>
 *
 * @author Rohtash Lakra
 * @created 1/22/21 1:06 PM
 */
@RefreshScope
@RestController
public class ConfigClientController {

    @Value("${message:Hello Config Client}")
    private String message;

    /**
     * http://localhost:8164/message
     * <p>
     *
     * @return
     */
    @GetMapping("/message")
    public String getMessage() {
        return this.message;
    }
}
