package com.instinctools.controllers.Dto;

import com.instinctools.entities.devEntities.Developer;
import lombok.Data;

@Data
public class DesiredJobDto {
    private String desiredJobTitle;

    private String desiredJobType;

    private Long desiredSalary;

    private String desiredSalaryPeriod;

    private Developer developer;
}
