package com.instinctools.entities.devEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DesiredJob {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String desiredJobTitle;

    private String desiredJobType;

    private Long desiredSalary;

    private String desiredSalaryPeriod;

    @OneToOne(fetch = FetchType.LAZY)
    private Developer developer;
}
