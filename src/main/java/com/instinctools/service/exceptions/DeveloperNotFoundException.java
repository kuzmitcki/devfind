package com.instinctools.service.exceptions;

public class DeveloperNotFoundException extends Exception {

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message error message
     */
    public DeveloperNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message   error message
     * @param exception exception
     */
    public DeveloperNotFoundException(final String message, final Exception exception) {
        super(message, exception);
    }
}
