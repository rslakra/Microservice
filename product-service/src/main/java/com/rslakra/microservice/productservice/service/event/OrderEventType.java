package com.rslakra.microservice.productservice.service.event;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:27 PM
 */
public enum OrderEventType {
    CREATE_ORDER,
    ORDER_CREATED,
    ORDER_CANCELLED,
    ORDER_FAILED;
}
