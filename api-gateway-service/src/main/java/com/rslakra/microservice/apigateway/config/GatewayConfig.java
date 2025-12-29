package com.rslakra.microservice.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway route configuration.
 * <p>
 * Configures routes for services that have context paths.
 * The discovery locator automatically strips the service name prefix,
 * but services with context paths need the prefix preserved.
 *
 * @author Rohtash Lakra
 * @created 12/28/24
 */
@Configuration
public class GatewayConfig {

    /**
     * Configures routes for product-service.
     * <p>
     * Since product-service has a context path of /product-service,
     * we need to preserve the full path when routing. The discovery locator
     * automatically strips the service name prefix, but we need to keep it
     * because product-service expects the context path.
     * <p>
     * This custom route takes precedence over the discovery locator route
     * and preserves the full path including the context path.
     *
     * @param builder RouteLocatorBuilder
     * @return RouteLocator
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("product-service", r -> r
                .path("/product-service/**")
                .filters(f -> f
                    // Gateway's discovery locator strips the service name prefix by default
                    // Since product-service has a context path, we need to preserve it
                    // This rewrite ensures the full path including context path is sent
                    // Input: /product-service/products -> Output: /product-service/products
                    // (This preserves the path as-is, preventing Gateway from stripping it)
                    .rewritePath("/product-service/(?<segment>.*)", "/product-service/${segment}")
                )
                .uri("lb://product-service")
            )
            .build();
    }
}

