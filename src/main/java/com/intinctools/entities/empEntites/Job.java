package com.intinctools.entities.empEntites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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

    private Long salaryPeriod;

    private String jobLocation;

    private String country;

    @ManyToOne
    private Employee employee;

    public Job(String title) {
        this.title = title;
    }

    public Job(String title, String fullDescription, String desiredExperience, Long fromSalary, Long toSalary,
               String jobType, Long salaryPeriod, String jobLocation, String country) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
