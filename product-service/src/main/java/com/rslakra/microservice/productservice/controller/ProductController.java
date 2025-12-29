package com.rslakra.microservice.productservice.controller;

import com.rslakra.microservice.productservice.persistence.entity.Product;
import com.rslakra.microservice.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for product administration UI.
 *
 * @author Rohtash Lakra
 * @created 12/28/24
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Shows the product list.
     *
     * @param model the model
     * @return the product list view
     */
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("pageTitle", "Products");
        model.addAttribute("headerTitle", "📦 Product Management");
        model.addAttribute("headerSubtitle", "View and manage all products");
        model.addAttribute("activeNav", "products");
        return "product/listProducts";
    }

    /**
     * Shows the form to create a new product.
     *
     * @param model the model
     * @return the product form view
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);
        model.addAttribute("pageTitle", "New Product");
        model.addAttribute("headerTitle", "➕ New Product");
        model.addAttribute("headerSubtitle", "Create a new product");
        model.addAttribute("activeNav", "new");
        return "product/editProduct";
    }

    /**
     * Shows the form to edit an existing product.
     *
     * @param id    the product ID
     * @param model the model
     * @return the product form view
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return productService.getById(id)
            .map(product -> {
                model.addAttribute("product", product);
                model.addAttribute("isEdit", true);
                model.addAttribute("pageTitle", "Edit Product");
                model.addAttribute("headerTitle", "✏️ Edit Product");
                model.addAttribute("headerSubtitle", "Update product information");
                model.addAttribute("activeNav", "products");
                return "product/editProduct";
            })
            .orElse("redirect:/products");
    }

    /**
     * Creates a new product.
     *
     * @param product           the product to create
     * @param redirectAttributes redirect attributes
     * @return redirect to product list
     */
    @PostMapping
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create product: " + e.getMessage());
        }
        return "redirect:/products";
    }

    /**
     * Updates an existing product.
     *
     * @param id                the product ID
     * @param product           the product to update
     * @param redirectAttributes redirect attributes
     * @return redirect to product list
     */
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            product.setId(id);
            productService.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update product: " + e.getMessage());
        }
        return "redirect:/products";
    }

    /**
     * Deletes a product.
     *
     * @param id                the product ID
     * @param redirectAttributes redirect attributes
     * @return redirect to product list
     */
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete product: " + e.getMessage());
        }
        return "redirect:/products";
    }

    /**
     * Shows the CSV upload page.
     *
     * @param model the model
     * @return the CSV upload view
     */
    @GetMapping("/upload")
    public String showUploadForm(Model model, jakarta.servlet.http.HttpServletRequest request) {
        model.addAttribute("pageTitle", "Upload Products");
        model.addAttribute("headerTitle", "📤 Upload Products");
        model.addAttribute("headerSubtitle", "Import products from CSV file");
        model.addAttribute("activeNav", "upload");
        
        // Check for error message in session (set by filter)
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        if (session != null) {
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (errorMessage != null) {
                model.addAttribute("errorMessage", errorMessage);
                session.removeAttribute("errorMessage"); // Remove after displaying
            }
        }
        
        return "product/upload";
    }

    /**
     * Handles CSV file upload and imports products.
     *
     * @param file              the uploaded CSV file
     * @param redirectAttributes redirect attributes
     * @return redirect to product list
     */
    @PostMapping("/upload")
    public String uploadCsv(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Please select a CSV file to upload.");
                return "redirect:/products/upload";
            }

            if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
                redirectAttributes.addFlashAttribute("errorMessage", "Please upload a CSV file (.csv extension).");
                return "redirect:/products/upload";
            }

            String csvContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            int importedCount = productService.importFromCsv(csvContent);
            redirectAttributes.addFlashAttribute("successMessage",
                String.format("Successfully imported %d product(s) from CSV file!", importedCount));
            return "redirect:/products";
        } catch (org.springframework.web.multipart.MaxUploadSizeExceededException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "File size exceeds the maximum allowed limit (100MB). Please reduce the file size or increase the limit by setting MAX_FILE_SIZE environment variable.");
            return "redirect:/products/upload";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Failed to import products: " + e.getMessage());
            return "redirect:/products/upload";
        }
    }

    /**
     * Downloads all products as a CSV file.
     * <p>
     * Exports all products from the database in CSV format matching the import format.
     *
     * @return CSV file with all products
     */
    @GetMapping("/download")
    public org.springframework.http.ResponseEntity<String> download() {
        try {
            List<Product> products = productService.getAll();
            
            // Generate CSV content
            StringWriter writer = new StringWriter();
            try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("name", "brand", "description", "isbn", "sku", "image_url"))) {
                
                for (Product product : products) {
                    csvPrinter.printRecord(
                        product.getName() != null ? product.getName() : "",
                        product.getBrand() != null ? product.getBrand() : "",
                        product.getDescription() != null ? product.getDescription() : "",
                        product.getIsbn() != null ? product.getIsbn() : "",
                        product.getSku() != null ? product.getSku() : "",
                        product.getImageUrl() != null ? product.getImageUrl() : ""
                    );
                }
            }
            
            String csvContent = writer.toString();
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.add("Content-Type", "text/csv; charset=UTF-8");
            headers.add("Content-Disposition", "attachment; filename=products_export.csv");
            
            return org.springframework.http.ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
        } catch (IOException e) {
            LOGGER.error("Failed to generate CSV export: {}", e.getMessage(), e);
            // If CSV generation fails, return empty CSV with header
            String csvContent = "name,brand,description,isbn,sku,image_url\n";
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.add("Content-Type", "text/csv; charset=UTF-8");
            headers.add("Content-Disposition", "attachment; filename=products_export.csv");
            
            return org.springframework.http.ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
        }
    }
}

