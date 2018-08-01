package com.instinctools.entities.devEntities.dto;

import com.instinctools.entities.devEntities.Developer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DesiredJobDTO {

    private String desiredJobTitle;

    private String desiredJobType;

    private Long desiredSalary;

    private String desiredSalaryPeriod;

    private Developer developer;
}
