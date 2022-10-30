package com.rslakra.microservices.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rohtash Lakra
 * @created 10/24/22 5:43 PM
 */
@RefreshScope
@RestController
public class MessageRestController {

    @Value("${message:Hello, Lakra!}")
    private String message;

    @RequestMapping("/message")
    public String getMessage() {
        return this.message;
    }
}
