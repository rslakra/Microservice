package com.devamatre.microservice.commonservice.exception;

/**
 * @author Rohtash Lakra
 * @created 7/27/23 8:39 PM
 */
public class DeserializationException extends Exception {

    /**
     *
     */
    public DeserializationException() {
    }

    /**
     * @param message
     */
    public DeserializationException(String message) {
        super(message);
    }

    /**
     * @param pattern
     * @param args
     */
    public DeserializationException(final String pattern, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)));
    }


    /**
     * @param message
     * @param cause
     */
    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param pattern
     * @param cause
     * @param args
     */
    public DeserializationException(final String pattern, final Throwable cause, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)), cause);
    }

    /**
     * @param cause
     */
    public DeserializationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DeserializationException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
