package com.devamatre.microservice.commonservice.exception;

/**
 * Thrown when the record already exists.
 *
 * @author Rohtash Lakra
 * @created 7/27/23 8:39 PM
 */
public class AlreadyExistsException extends Exception {

    /**
     *
     */
    public AlreadyExistsException() {
    }

    /**
     * @param message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }

    /**
     * @param pattern
     * @param args
     */
    public AlreadyExistsException(final String pattern, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)));
    }


    /**
     * @param message
     * @param cause
     */
    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param pattern
     * @param cause
     * @param args
     */
    public AlreadyExistsException(final String pattern, final Throwable cause, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)), cause);
    }

    /**
     * @param cause
     */
    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
