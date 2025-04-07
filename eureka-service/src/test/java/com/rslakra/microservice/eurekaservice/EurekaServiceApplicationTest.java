package com.rslakra.microservice.eurekaservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Rohtash Lakra
 * @created 1/21/21 3:41 PM
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EurekaServiceApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Returns the <code>TestRestTemplate</code> object.
     *
     * @return
     */
    private TestRestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * @param endPoint
     * @return
     */
    private String toRequest(String endPoint) {
        return "http://localhost:" + port + "/" + endPoint;
    }

    /**
     * Tests the Eureka Service has started.
     */
    @Test
    public void testStartEurekaService() {
        ResponseEntity<String> response = getRestTemplate().getForEntity(toRequest("/eureka/apps"), String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
