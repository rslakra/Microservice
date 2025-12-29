package com.rslakra.microservice.productclient.controller;

import com.rslakra.microservice.productclient.proxy.ApiProxy;
import com.rslakra.microservice.productservice.persistence.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * View Controller for Product UI.
 * This controller serves HTML views using Thymeleaf templates.
 *
 * @author Rohtash Lakra
 * @created 12/28/24
 */
@Controller
@RequestMapping("/")
public class ProductViewController {

    private final ApiProxy apiProxy;

    /**
     * @param apiProxy
     */
    @Autowired
    public ProductViewController(ApiProxy apiProxy) {
        this.apiProxy = apiProxy;
    }

    /**
     * Displays the products page.
     *
     * @param model
     * @return
     */
    @GetMapping
    public String index(Model model) {
        try {
            List<Product> products = apiProxy.getAllProducts();
            model.addAttribute("products", products);
            model.addAttribute("productCount", products != null ? products.size() : 0);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            // Provide more helpful error messages
            if (errorMessage != null && errorMessage.contains("Load balancer does not contain an instance")) {
                errorMessage = "API Gateway service is not available. Please ensure:\n" +
                    "1. Eureka Service is running (default: http://localhost:8761)\n" +
                    "2. API Gateway Service is running and registered with Eureka\n" +
                    "3. Wait a few seconds for service registration to complete\n" +
                    "Original error: " + errorMessage;
            }
            model.addAttribute("error", errorMessage);
            model.addAttribute("products", List.of());
            model.addAttribute("productCount", 0);
        }
        return "products";
    }

    /**
     * Displays the products page (alternative route).
     *
     * @param model
     * @return
     */
    @GetMapping("/products")
    public String products(Model model) {
        return index(model);
    }
}

