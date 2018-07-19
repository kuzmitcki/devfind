package com.intinctools.entities.empEntites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String fullDescription;

    private String desiredExperience;
    private Long fromSalary;
    private Long toSalary;
    private String jobType;
    private String qualifications;
    private String salaryPeriod;
    private String jobLocation;
    private String country;
    @ManyToOne
    private Employee employee;

    public Job(String title) {
        this.title = title;
    }

    public Job(String title, String fullDescription, String desiredExperience, Long fromSalary, Long toSalary, String jobType, String salaryPeriod, String jobLocation, String country) {
        this.title = title;
        this.fullDescription = fullDescription;
        this.desiredExperience = desiredExperience;
        this.fromSalary = fromSalary;
        this.toSalary = toSalary;
        this.jobType = jobType;
        this.salaryPeriod = salaryPeriod;
        this.jobLocation = jobLocation;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", desiredExperience='" + desiredExperience + '\'' +
                ", fromSalary=" + fromSalary +
                ", toSalary=" + toSalary +
                ", jobType='" + jobType + '\'' +
                ", salaryPeriod='" + salaryPeriod + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
