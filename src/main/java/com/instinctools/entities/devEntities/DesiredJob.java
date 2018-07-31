package com.instinctools.entities.devEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
