package com.rslakra.microservice.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Global filter for Spring Cloud Gateway that logs all incoming requests.
 * <p>
 * This filter replaces the Zuul filter and provides similar functionality using Spring Cloud Gateway's reactive model.
 * <p>
 * The filter runs before the request is routed to downstream services (pre-filter).
 *
 * @author Rohtash Lakra
 * @created 3/2/24 12:09 PM
 */
@Component
public class GatewayLoginFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayLoginFilter.class);

    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link GatewayFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.debug("+filter()");
        LOGGER.debug("Request is filtered!");
        
        var request = exchange.getRequest();
        LOGGER.debug("request:{}, requestURI:{}", request, request.getURI());
        
        LOGGER.debug("-filter()");
        return chain.filter(exchange);
    }

    /**
     * Returns the order value of this object, with a higher value meaning greater priority.
     * Lower values have higher priority.
     *
     * @return the order value
     */
    @Override
    public int getOrder() {
        return 1;
    }
}

