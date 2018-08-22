package com.instinctools.entities.devEntities;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;

@Data
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
}