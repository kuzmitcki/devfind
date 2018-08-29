package com.instinctools.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JobNotFoundException extends Exception {

    /**
     * Creates new instance of job not found exception.
     *
     * @param message error message
     */
    public JobNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates new instance of job not found exception.
     *
     * @param message   error message
     * @param exception exception
     */
    public JobNotFoundException(final String message, final Exception exception) {
        super(message, exception);
    }
}
