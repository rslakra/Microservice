package com.rslakra.microservice.productservice.service;

import com.rslakra.microservice.productservice.service.event.OrderEventHandler;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:16 PM
 */
public interface OrderService extends OrderEventHandler {

    /**
     * @param orderId
     */
    void acceptOrder(Long orderId);

    /**
     * @param orderId
     */
    void rejectOrder(Long orderId);

    /**
     * @param orderId
     */
    void cancelOrder(Long orderId);
}
