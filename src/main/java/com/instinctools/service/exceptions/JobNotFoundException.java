package com.instinctools.service.exceptions;

public class JobNotFoundException extends Exception {

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message error message
     */
    public JobNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message   error message
     * @param exception exception
     */
    public JobNotFoundException(final String message, final Exception exception) {
        super(message, exception);
    }
}
