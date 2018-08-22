package com.instinctools.controllers.Dto;


import com.instinctools.entities.devEntities.Developer;
import lombok.Data;


@Data
public class EducationDto {
    private String degree;

    private String place;

    private String fieldOfStudy;

    private String cityOfEducation;

    private String monthFrom;

    private String monthTo;

    private String yearFrom;

    private String yearTo;
}
