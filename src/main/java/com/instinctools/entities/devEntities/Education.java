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

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    public Education(final String degree, final  String place, final  String fieldOfStudy, final  String cityOfEducation, final  String monthFrom,
                     final  String monthTo, final  String yearFrom, final  String yearTo, final  Developer developer) {
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
