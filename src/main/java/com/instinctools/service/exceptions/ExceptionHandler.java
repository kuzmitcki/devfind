package com.instinctools.service.exceptions;

import com.instinctools.controllers.Dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * Handle resource not found exception.
     *
     * @param exception an exception
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(final ResourceNotFoundException exception) {
        LOGGER.warn("Resource not found", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.aResponseDTO(exception.getMessage()));
    }

    /**
     * Handle developer not found exception.
     *
     * @param exception an exception
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(DeveloperNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleDeveloperNotFoundException(final DeveloperNotFoundException exception) {
        LOGGER.warn("Developer not found", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.aResponseDTO(exception.getMessage()));
    }

    /**
     * Handle education not found exception.
     *
     * @param exception an exception
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(EducationNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleEducationNotFoundException(final EducationNotFoundException exception) {
        LOGGER.warn("Education not found", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.aResponseDTO(exception.getMessage()));
    }

    /**
     * Handle job not found exception.
     *
     * @param exception an exception
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(JobNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleJobNotFoundException(final JobNotFoundException exception) {
        LOGGER.warn("Job not found", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.aResponseDTO(exception.getMessage()));
    }

    /**
     * Handle specialization not found exception.
     *
     * @param exception an exception
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(SpecializationNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleSpecializationNotFoundException(final SpecializationNotFoundException exception) {
        LOGGER.warn("Specialization not found", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.aResponseDTO(exception.getMessage()));
    }

    /**
     * Handle work experience not found exception.
     *
     * @param exception an exception
     * @return an error message
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(WorkExperienceNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleWorkExperienceNotFoundException(final WorkExperienceNotFoundException exception) {
        LOGGER.warn("Specialization not found", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.aResponseDTO(exception.getMessage()));
    }
}
