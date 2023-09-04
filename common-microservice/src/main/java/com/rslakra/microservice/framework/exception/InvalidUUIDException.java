package com.rslakra.microservice.framework.exception;

/**
 * Thrown when the provided string is not a valid <code>UUID</code>.
 *
 * @author Rohtash Lakra
 * @created 7/27/23 8:39 PM
 */
public class InvalidUUIDException extends Exception {

    /**
     *
     */
    public InvalidUUIDException() {
    }

    /**
     * @param message
     */
    public InvalidUUIDException(String message) {
        super(message);
    }

    /**
     * @param pattern
     * @param args
     */
    public InvalidUUIDException(final String pattern, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)));
    }


    /**
     * @param message
     * @param cause
     */
    public InvalidUUIDException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param pattern
     * @param cause
     * @param args
     */
    public InvalidUUIDException(final String pattern, final Throwable cause, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)), cause);
    }

    /**
     * @param cause
     */
    public InvalidUUIDException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InvalidUUIDException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
