package com.devamatre.microservice.commonservice.exception;

/**
 * Thrown when we can't find the entity in the database.
 *
 * @author Rohtash Lakra
 * @created 7/27/23 8:39 PM
 */
public class RecordNotFoundException extends Exception {

    /**
     *
     */
    public RecordNotFoundException() {
    }

    /**
     * @param message
     */
    public RecordNotFoundException(String message) {
        super(message);
    }

    /**
     * @param pattern
     * @param args
     */
    public RecordNotFoundException(final String pattern, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)));
    }


    /**
     * @param message
     * @param cause
     */
    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param pattern
     * @param cause
     * @param args
     */
    public RecordNotFoundException(final String pattern, final Throwable cause, final Object... args) {
        this(String.format("Record already exists with %s!", String.format(pattern, args)), cause);
    }

    /**
     * @param cause
     */
    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public RecordNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
