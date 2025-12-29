package com.rslakra.microservice.productclient.proxy;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Act as proxy class in between API and client.
 *
 * <code>@FeignClient(name = "api-gateway-service")</code> annotation will enable the communication from the Client
 * application to API gateway application.
 *
 * Spring Cloud LoadBalancer (replacing deprecated Netflix Ribbon) automatically handles load balancing
 * when service discovery is enabled. The service name in the URL path ("product-service") tells the
 * API gateway where to route the request.
 *
 * <code>product-service</code> should be the name of Server application.
 *
 * @author Rohtash Lakra
 * @created 3/2/24 3:56 PM
 */
@FeignClient(name = "api-gateway-service")
public interface ApiProxy {

    /**
     * Gets all products from the product service via API gateway.
     * Uses the REST API endpoint that returns JSON.
     *
     * @return list of all products
     */
    @GetMapping("product-service/api/products")
    public List<Product> getAllProducts();

}
