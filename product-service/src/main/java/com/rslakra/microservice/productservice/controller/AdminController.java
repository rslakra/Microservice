package com.rslakra.microservice.productservice.controller;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import com.rslakra.microservice.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for product administration UI.
 *
 * @author Rohtash Lakra
 * @created 12/28/24
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Shows the product management dashboard.
     *
     * @param model the model
     * @return the dashboard view
     */
    @GetMapping
    public String index(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("productCount", products.size());
        model.addAttribute("pageTitle", "Admin Dashboard");
        model.addAttribute("headerTitle", "⚙️ Product Service Admin");
        model.addAttribute("headerSubtitle", "Manage products, stock, and orders");
        model.addAttribute("activeNav", "dashboard");
        return "index";
    }

}

