package com.instinctools.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeveloperNotFoundException extends RuntimeException {

    /**
     * Creates new instance of developer not found exception.
     *
     * @param message error message
     */
    public DeveloperNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates new instance of developer not found exception.
     *
     * @param message   error message
     * @param exception exception
     */
    public DeveloperNotFoundException(final String message, final Exception exception) {
        super(message, exception);
    }
}
