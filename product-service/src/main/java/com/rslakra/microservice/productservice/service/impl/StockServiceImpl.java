package com.rslakra.microservice.productservice.service.impl;

import com.rslakra.microservice.productservice.service.StockService;
import org.springframework.stereotype.Service;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:22 PM
 */
@Service
public class StockServiceImpl implements StockService {

    /**
     * @param orderId
     */
    @Override
    public void reserveOrderStocks(Long orderId) {

    }

    /**
     * @param orderId
     */
    @Override
    public void releaseOrderStocks(Long orderId) {

    }
}
