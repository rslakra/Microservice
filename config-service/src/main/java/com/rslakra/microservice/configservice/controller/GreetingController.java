package com.rslakra.microservice.configservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rohtash Lakra
 * @created 10/25/22 1:56 PM
 */
@RefreshScope
@RestController
public class GreetingController {

    @Value("${spring.application.name}")
    private String appName;

    /**
     * @return
     */
    @GetMapping("/greeting")
    public String greeting() {
        return String.format("%s welcomes you!", appName);
    }

}
