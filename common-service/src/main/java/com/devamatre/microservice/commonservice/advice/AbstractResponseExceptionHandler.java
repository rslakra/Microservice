package com.devamatre.microservice.commonservice.advice;

import com.devamatre.microservice.commonservice.exception.AlreadyExistsException;
import com.devamatre.microservice.commonservice.exception.InvalidValueException;
import com.devamatre.microservice.commonservice.exception.RecordNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

/**
 * The default exception handler that returns the error details to the caller.
 *
 * @author Rohtash Lakra
 * @created 7/25/23 4:07 PM
 */
public abstract class AbstractResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MESSAGES = "messages";

    /**
     * handles any Invalid Values, typically math errors
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Object> handleInvalidValueException(InvalidValueException ex, WebRequest webRequest) {
        return new ResponseEntity<>(createResponseBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * handles when trying to add an email that already exists
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleUserExists(AlreadyExistsException ex, WebRequest webRequest) {
        return new ResponseEntity<>(createResponseBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * handles when trying to get an entity that is not there (404)
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(RecordNotFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<>(createResponseBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * handles parameters that are outside specified values
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest webRequest) {
        return new ResponseEntity<>(createResponseBody(ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * handles any generic integrity errors the database might throw
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        if (ex.getCause() instanceof DataException) {
            DataException dataException = (DataException) ex.getCause();
            errorMessage = "SQL Exception: " + dataException.getSQLException().getMessage();
        } else if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException dbException = (ConstraintViolationException) ex.getCause();
            errorMessage = "SQL Exception: " + dbException.getSQLException().getMessage();
        } else if (ex.getCause() instanceof SQLException) {
            SQLException sqlException = (SQLException) ex.getCause();
            errorMessage = "SQL Exception: " + sqlException.getMessage();
        }

        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.CONFLICT);
    }

    /**
     * handles any generic argument exceptions the database might throw
     *
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {
        String errorMessage = ex.getMessage();
        if (ex.getCause() instanceof DataException) {
            DataException dataException = (DataException) ex.getCause();
            errorMessage = "SQL Exception: " + dataException.getSQLException().getMessage();
        }

        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.CONFLICT);
    }

    /**
     * @param ex
     * @param headers
     * @param status
     * @param webRequest
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest webRequest) {
        // Spring already has a method handler for invalid arguments, so we need to override it instead of just handle it
        String errorField = ex.getBindingResult().getFieldError().getField();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        // clean up the error message as the one in the exception is a bit hard to read
        String errorMessage = String.format("Validation error in field <%s> : %s", errorField, message);
        return new ResponseEntity<>(createResponseBody(errorMessage), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param message
     * @return
     */
    protected AbstractResponse createResponseBody(String message) {
        AbstractResponse abstractResponse = AbstractResponse.builder().build();
        abstractResponse.addMessage(message);
        return abstractResponse;
    }

}
