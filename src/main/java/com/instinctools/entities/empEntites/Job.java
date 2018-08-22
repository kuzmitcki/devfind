package com.instinctools.entities.empEntites;

import lombok.Data;
import org.hibernate.annotations.Type;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

import java.util.Objects;

@Data
@Entity
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


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Job job = (Job) o;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
