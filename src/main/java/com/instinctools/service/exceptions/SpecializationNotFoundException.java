package com.instinctools.service.exceptions;

public class SpecializationNotFoundException extends Exception {

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message error message
     */
    public SpecializationNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message   error message
     * @param exception exception
     */
    public SpecializationNotFoundException(final String message, final Exception exception) {
        super(message, exception);
    }
}