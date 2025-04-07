package com.devamatre.microservice.commonservice.exception;

/**
 * Thrown if the object is not serializable.
 *
 * @author Rohtash Lakra
 * @created 7/27/23 8:39 PM
 */
public class SerializationException extends Exception {

    /**
     *
     */
    public SerializationException() {
    }

    /**
     * @param message
     */
    public SerializationException(String message) {
        super(message);
    }

    /**
     * @param pattern
     * @param args
     */
    public SerializationException(final String pattern, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)));
    }


    /**
     * @param message
     * @param cause
     */
    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param pattern
     * @param cause
     * @param args
     */
    public SerializationException(final String pattern, final Throwable cause, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)), cause);
    }

    /**
     * @param cause
     */
    public SerializationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SerializationException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
