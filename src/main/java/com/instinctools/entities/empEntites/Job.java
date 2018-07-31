package com.instinctools.entities.empEntites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

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
