package com.rslakra.microservice.productservice.service.event;

/**
 * @author Rohtash Lakra
 * @created 3/4/24 6:53 PM
 */
public interface EventHandler<T extends EventType> {

    /**
     * Handles the event.
     *
     * @param eventType
     */
    void handleEvent(T eventType);

}
