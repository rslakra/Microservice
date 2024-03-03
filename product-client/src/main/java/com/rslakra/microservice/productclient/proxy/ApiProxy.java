package com.rslakra.microservice.productclient.proxy;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Act as proxy class in between API and client.
 *
 * <code>@FeignClient(name = "api-gateway-service")</code> annotation will enable the communication from the Client
 * application to API gateway application.
 *
 * <code>@RibbonClient(name = "product-service")</code> annotation will tell the API gateway application to where
 * the request has to go.
 *
 * <code>product-service</code> should be the name of Server application.
 *
 * @author Rohtash Lakra
 * @created 3/2/24 3:56 PM
 */
@FeignClient(name = "api-gateway-service")
@RibbonClient(name = "product-service")
public interface ApiProxy {

    /**
     * @return
     */
    @GetMapping("product-service/products")
    public List<Product> getAllProducts();

}
