package com.instinctools.service.exceptions;

public class EducationNotFoundException extends Exception {

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message error message
     */
    public EducationNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates new instance of resource not found exception.
     *
     * @param message   error message
     * @param exception exception
     */
    public EducationNotFoundException(final String message, final Exception exception) {
        super(message, exception);
    }
}
