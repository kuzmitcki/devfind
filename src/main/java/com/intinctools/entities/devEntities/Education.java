package com.intinctools.entities.devEntities;

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
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String degree;

    private String place;

    private String fieldOfStudy;

    private String cityOfEducation;

    private String monthFrom;

    private String monthTo;

    private String yearFrom;

    private String yearTo;

    @ManyToOne
    private Developer developer;

    public Education(String degree, String place, String fieldOfStudy, String cityOfEducation, String monthFrom,
                     String monthTo, String yearFrom, String yearTo, Developer developer) {
        this.degree = degree;
        this.place = place;
        this.fieldOfStudy = fieldOfStudy;
        this.cityOfEducation = cityOfEducation;
        this.monthFrom = monthFrom;
        this.monthTo = monthTo;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.developer = developer;
    }
}
