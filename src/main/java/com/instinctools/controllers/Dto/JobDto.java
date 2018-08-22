package com.instinctools.controllers.Dto;



import lombok.Data;
import org.hibernate.annotations.Type;

@Data
public class JobDto {

    private String title;

    @Type(type = "text")
    private String fullDescription;

    @Type(type = "text")
    private String desiredExperience;

    private Long fromSalary;

    private Long toSalary;

    private String jobType;

    @Type(type = "text")
    private String qualifications;

    private Long salaryPeriod;

    private String jobLocation;

    private String country;

    private String company;
}
