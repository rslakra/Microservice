package com.devamatre.microservice.commonservice.advice;

import com.devamatre.appsuite.core.BeanUtils;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the <code>statusCode</code> and error messages.
 *
 * @author Rohtash Lakra
 * @created 7/27/23 8:39 PM
 */
@SuperBuilder
public class AbstractResponse {

    private Integer statusCode;
    private List<String> messages;

    /**
     * @param message
     */
    public void addMessage(String message) {
        if (BeanUtils.isNull(messages)) {
            messages = new ArrayList<>();
        }

        this.messages.add(message);
    }

}
