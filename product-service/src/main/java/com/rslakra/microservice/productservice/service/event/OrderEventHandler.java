package com.rslakra.microservice.productservice.service.event;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:24 PM
 */
public interface OrderEventHandler extends EventHandler {

    /**
     *
     * @param createOrderEvent
     */
    void handleEvent(CreateOrderEvent createOrderEvent);

}
