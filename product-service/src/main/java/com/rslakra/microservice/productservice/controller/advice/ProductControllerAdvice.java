package com.rslakra.microservice.productservice.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller advice to handle exceptions for UI controllers.
 *
 * @author Rohtash Lakra
 * @created 12/28/24
 */
@ControllerAdvice
public class ProductControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductControllerAdvice.class);

    @Value("${spring.servlet.multipart.max-file-size:100MB}")
    private String maxFileSize;

    /**
     * Handles file upload size exceeded exception.
     * <p>
     * This exception is thrown by Spring's multipart resolver before the controller method is invoked,
     * so it must be handled at the @ControllerAdvice level.
     * <p>
     * When the exception occurs during multipart parsing, the response might already be committed.
     * If the response is committed, we can't render a new view, so we need to handle it differently.
     *
     * @param ex                  the exception
     * @param request            the HTTP request
     * @param response           the HTTP response
     * @param redirectAttributes redirect attributes (may not work if response is committed)
     * @return ModelAndView rendering the upload page with error message, or null if response is committed
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadSizeExceeded(
        MaxUploadSizeExceededException ex,
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes) {
        
        LOGGER.error("File upload size exceeded: {}", ex.getMessage(), ex);
        
        String errorMessage = String.format(
            "File size exceeds the maximum allowed limit (%s). " +
            "Please reduce the file size or increase the limit by setting MAX_FILE_SIZE environment variable.",
            maxFileSize);
        
        // Check if response is already committed
        if (response.isCommitted()) {
            LOGGER.warn("Response already committed, cannot render error page. Error: {}", errorMessage);
            // Response is committed, we can't send a new response
            // The client will see a connection reset, but at least we logged the error
            return null;
        }
        
        // Reset the response if possible
        try {
            if (!response.isCommitted()) {
                response.reset();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IllegalStateException e) {
            LOGGER.warn("Cannot reset response: {}", e.getMessage());
            return null;
        }
        
        // Render the upload page directly with error message
        ModelAndView mav = new ModelAndView("product/upload");
        mav.addObject("errorMessage", errorMessage);
        mav.addObject("pageTitle", "Upload Products");
        mav.addObject("headerTitle", "📤 Upload Products");
        mav.addObject("headerSubtitle", "Import products from CSV file");
        mav.addObject("activeNav", "upload");
        
        // Also try to add as flash attribute in case redirect works
        try {
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        } catch (Exception e) {
            LOGGER.debug("Could not add flash attribute: {}", e.getMessage());
        }
        
        return mav;
    }
}

