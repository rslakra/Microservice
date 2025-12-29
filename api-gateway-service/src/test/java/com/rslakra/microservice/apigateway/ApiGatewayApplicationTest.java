package com.rslakra.microservice.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Rohtash Lakra
 * @created 1/21/21 3:41 PM
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.cloud.config.enabled=false",
    "eureka.client.enabled=false",
    "spring.cloud.gateway.discovery.locator.enabled=false",
    "spring.cloud.compatibility-verifier.enabled=false"
})
public class ApiGatewayApplicationTest {

    @Test
    void contextLoads() {
    }

}
