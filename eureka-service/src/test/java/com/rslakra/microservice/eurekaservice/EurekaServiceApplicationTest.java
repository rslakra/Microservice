package com.rslakra.microservice.eurekaservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Rohtash Lakra
 * @created 1/21/21 3:41 PM
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.cloud.config.enabled=false",
    "spring.cloud.config.import-check.enabled=false",
    "eureka.client.register-with-eureka=false",
    "eureka.client.fetch-registry=false",
    "spring.cloud.compatibility-verifier.enabled=false"
})
public class EurekaServiceApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests that the application context loads successfully.
     */
    @Test
    public void contextLoads() {
        // Test passes if context loads without errors
        assertNotNull(restTemplate);
    }

    /**
     * Tests the Eureka Service has started.
     */
    @Test
    public void testStartEurekaService() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/eureka/apps", 
            String.class
        );
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
