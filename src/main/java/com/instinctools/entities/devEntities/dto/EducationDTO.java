package com.instinctools.entities.devEntities.dto;


import com.instinctools.entities.devEntities.Developer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EducationDTO {
    private String degree;

    private String place;

    private String fieldOfStudy;

    private String cityOfEducation;

    private String monthFrom;

    private String monthTo;

    private String yearFrom;

    private String yearTo;

    private Developer developer;

    public EducationDTO(String degree, String place, String fieldOfStudy,
                        String cityOfEducation, String monthFrom, String monthTo,
                        String yearFrom, String yearTo) {
        this.degree = degree;
        this.place = place;
        this.fieldOfStudy = fieldOfStudy;
        this.cityOfEducation = cityOfEducation;
        this.monthFrom = monthFrom;
        this.monthTo = monthTo;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
    }
}
