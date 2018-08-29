package com.instinctools.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message error message
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message   error message
     * @param exception exception
     */
    public ResourceNotFoundException(final String message, final Exception exception) {
        super(message, exception);
    }
}
