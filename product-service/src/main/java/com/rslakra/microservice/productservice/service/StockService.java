package com.rslakra.microservice.productservice.service;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:16 PM
 */
public interface StockService {

    /**
     * @param orderId
     */
    void reserveOrderStocks(Long orderId);

    /**
     * @param orderId
     */
    void releaseOrderStocks(Long orderId);

}
