package com.rslakra.microservice.productservice.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;

/**
 * Filter to handle MaxUploadSizeExceededException before the response is committed.
 * This filter catches the exception at a lower level and redirects to the upload page with an error message.
 *
 * @author Rohtash Lakra
 * @created 12/28/24
 */
@Component
@Order(1)
public class MultipartExceptionFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultipartExceptionFilter.class);

    @Value("${spring.servlet.multipart.max-file-size:100MB}")
    private String maxFileSize;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        try {
            chain.doFilter(request, response);
        } catch (NestedServletException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof MaxUploadSizeExceededException) {
                LOGGER.error("File upload size exceeded in filter: {}", rootCause.getMessage());
                handleMaxUploadSizeExceeded(httpRequest, httpResponse, (MaxUploadSizeExceededException) rootCause);
                return;
            }
            throw e;
        } catch (MaxUploadSizeExceededException e) {
            LOGGER.error("File upload size exceeded in filter: {}", e.getMessage());
            handleMaxUploadSizeExceeded(httpRequest, httpResponse, e);
            return;
        }
    }

    private void handleMaxUploadSizeExceeded(HttpServletRequest request, HttpServletResponse response,
        MaxUploadSizeExceededException ex) throws IOException {
        
        // Only handle if this is a POST request to the upload endpoint
        if ("POST".equals(request.getMethod()) && request.getRequestURI().contains("/products/upload")) {
            try {
                // Reset the response if not committed
                if (!response.isCommitted()) {
                    response.reset();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    // Store error message in session for the redirect
                    String errorMessage = String.format(
                        "File size exceeds the maximum allowed limit (%s). " +
                        "Please reduce the file size or increase the limit by setting MAX_FILE_SIZE environment variable.",
                        maxFileSize);
                    request.getSession().setAttribute("errorMessage", errorMessage);
                    response.sendRedirect(request.getContextPath() + "/products/upload");
                    return;
                }
            } catch (Exception e) {
                LOGGER.warn("Could not handle MaxUploadSizeExceededException: {}", e.getMessage());
            }
        }
        
        // If we can't handle it, rethrow as IOException
        throw new IOException("File upload size exceeded", ex);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}

