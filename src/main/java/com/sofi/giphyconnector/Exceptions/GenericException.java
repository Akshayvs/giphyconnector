package com.sofi.giphyconnector.Exceptions;

/**
 * This class prevents the internal error details from getting exposed in the API response.
 */
public class GenericException extends Exception {

    public GenericException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    public GenericException(String errorMessage) {
        super(errorMessage);
    }
}
