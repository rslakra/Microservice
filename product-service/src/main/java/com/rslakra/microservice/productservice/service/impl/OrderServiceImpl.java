package com.rslakra.microservice.productservice.service.impl;

import com.rslakra.microservice.productservice.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:21 PM
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * @param orderId
     */
    @Override
    public void acceptOrder(Long orderId) {

    }

    /**
     * @param orderId
     */
    @Override
    public void rejectOrder(Long orderId) {

    }

    /**
     * @param orderId
     */
    @Override
    public void cancelOrder(Long orderId) {

    }
}
