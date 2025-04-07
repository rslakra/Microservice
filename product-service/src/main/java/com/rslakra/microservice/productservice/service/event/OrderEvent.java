package com.rslakra.microservice.productservice.service.event;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:24 PM
 */
public interface OrderEvent extends EventType {

    /**
     * @param orderId
     */
    void setOrderId(Long orderId);

}
