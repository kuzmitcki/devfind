package com.instinctools.entities.devEntities;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;


@Data
@Entity
public class DesiredJob {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String desiredJobTitle;

    private String desiredJobType;

    private Long desiredSalary;

    private String desiredSalaryPeriod;

    @OneToOne
    private Developer developer;
}
