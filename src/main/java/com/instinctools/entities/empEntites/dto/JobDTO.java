package com.instinctools.entities.empEntites.dto;

import com.instinctools.entities.empEntites.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@AllArgsConstructor
public class JobDTO {

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

    private Employee employee;

    public JobDTO(String title, String jobLocation, String country) {
        this.title = title;
        this.jobLocation = jobLocation;
        this.country = country;
    }

    public JobDTO(String jobType, Long fromSalary, Long toSalary, Long salaryPeriod, String qualifications) {
        this.jobType = jobType;
        this.fromSalary = fromSalary;
        this.toSalary = toSalary;
        this.salaryPeriod = salaryPeriod;
        this.qualifications = qualifications;
    }
}
