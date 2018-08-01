package com.instinctools.entities.devEntities.dto;

import com.instinctools.entities.devEntities.Developer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@AllArgsConstructor
public class WorkExperienceDTO {

    private String jobTitle;

    private String company;

    private String city;

    private String monthFrom;

    private String monthTo;

    private String yearFrom;

    private String yearTo;

    @Type(type = "text")
    private String description;

    private Developer developer;


}
