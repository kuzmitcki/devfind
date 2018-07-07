package com.intinctools.entities.empEntites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String fullDescription;
    private String shortDescription;
    private String desiredExperience;
    private Long fromSalary;
    private Long toSalary;
    private String company;
    private String jobType;
    private String salaryPeriod;
    @ManyToOne
    private Employee employee;


    public Job(String title, String fullDescription, String shortDescription, String desiredExperience, Long fromSalary, Long toSalary, String company, String jobType, String salaryPeriod) {
        this.title = title;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.desiredExperience = desiredExperience;
        this.fromSalary = fromSalary;
        this.toSalary = toSalary;
        this.company = company;
        this.jobType = jobType;
        this.salaryPeriod = salaryPeriod;
    }
}
