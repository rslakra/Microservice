package com.rslakra.microservice.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 1/21/21 3:57 PM
 */
@RestController
public class EurekaClientController {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public EurekaClientController(final DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * @return
     */
    private DiscoveryClient getDiscoveryClient() {
        return this.discoveryClient;
    }


    /**
     * Returns the list of applications by name.
     *
     * @param appName
     * @return
     */
    @GetMapping("/services/{appName}")
    public List<ServiceInstance> findServicesByName(@PathVariable String appName) {
        return getDiscoveryClient().getInstances(appName);
    }

}
